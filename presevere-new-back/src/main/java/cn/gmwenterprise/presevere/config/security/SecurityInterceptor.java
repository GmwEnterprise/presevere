package cn.gmwenterprise.presevere.config.security;

import cn.gmwenterprise.presevere.common.Constants;
import cn.gmwenterprise.presevere.common.TokenHelper;
import cn.gmwenterprise.presevere.domain.SysPermission;
import cn.gmwenterprise.presevere.service.UserService;
import cn.gmwenterprise.presevere.vo.AjaxResult;
import cn.gmwenterprise.presevere.vo.Res;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Method;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class SecurityInterceptor implements HandlerInterceptor {
    private static final Logger log = LoggerFactory.getLogger(SecurityInterceptor.class);

    @Resource
    UserService userService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        log.info("SecurityInterceptor.preHandle");
        String[] permissions = getPermissions(handler);
        if (permissions != null && permissions.length > 0) {
            // 需要验证权限
            log.info("进入安全拦截器, 请求URL = [{}]", request.getRequestURL());

            // 获取token
            String token = request.getHeader("Authorization");
            if (!StringUtils.isEmpty(token)) {
                // 有token
                log.info("Token: [{}]", token);
                TokenPayload payload = TokenHelper.parseToken(token, TokenPayload.class);
                // 验证payload
                if (payload == null) {
                    // 解析失败
                    return noAccess(response, "禁止访问，需要登陆", Res.LOGIN_REQUIRED);
                }
                TokenPayloadVerificationResults result = validateAuthentication(payload, request.getRemoteHost());
                Integer userId = payload.getUserId();
                if (result == TokenPayloadVerificationResults.SUCCESS) {
                    List<SysPermission> permissionList = userService.getUserPermissionsByUserId(userId);
                    if (permissionList != null && permissionList.size() >= permissions.length) {
                        boolean hasPermission = permissionList.stream()
                            .map(SysPermission::getPermission)
                            .collect(Collectors.toList())
                            .containsAll(Arrays.asList(permissions));
                        // 验证有无权限
                        if (hasPermission) {
                            // 有
                            Authorization authorization = new Authorization();
                            authorization.setToken(token);
                            authorization.setTokenPayload(payload);
                            authorization.setPermissions(permissionList);
                            AuthorizationHolder.set(authorization);
                            return true;
                        } else {
                            return noAccess(response, "当前用户无访问权限", Res.UNAUTHORIZED);
                        }
                    } else {
                        // 用户权限连数量都不够
                        return noAccess(response, "当前用户无访问权限", Res.UNAUTHORIZED);
                    }
                } else if (result == TokenPayloadVerificationResults.WAIT_REFRESH) {
                    return needRefreshToken(response, userId);
                } else {
                    return noAccess(response, "禁止访问，需要登陆", Res.LOGIN_REQUIRED);
                }
            } else {
                // 无token
                return noAccess(response, "禁止访问，需要登陆", Res.LOGIN_REQUIRED);
            }
        }
        return true;
    }

    enum TokenPayloadVerificationResults {
        SUCCESS, ERROR, EXPIRED, WAIT_REFRESH
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        AuthorizationHolder.remove();
    }

    @Resource
    ObjectMapper objectMapper;

    private boolean noAccess(HttpServletResponse response, String errorMsg, Res status) throws IOException {
        response.setCharacterEncoding(Constants.UTF_8);
        response.setContentType(Constants.APPLICATION_JSON);
        PrintWriter writer = response.getWriter();
        writer.write(objectMapper.writeValueAsString(AjaxResult.res(status, errorMsg)));
        writer.flush();
        writer.close();
        return false;
    }

    private boolean needRefreshToken(HttpServletResponse response, Integer userId) throws IOException {
        response.setCharacterEncoding(Constants.UTF_8);
        response.setContentType(Constants.APPLICATION_JSON);
        PrintWriter writer = response.getWriter();
        AjaxResult ajaxResult = AjaxResult.res(Res.REFRESH_TOKEN, "需要刷新令牌");
        ajaxResult.setData(userId);
        writer.write(objectMapper.writeValueAsString(ajaxResult));
        writer.flush();
        writer.close();
        return false;
    }

    /**
     * 获取接口所需权限
     *
     * @param handler handler
     * @return 权限
     */
    private String[] getPermissions(Object handler) {
        if (handler instanceof HandlerMethod) {
            Method targetMethod = ((HandlerMethod) handler).getMethod();
            if (targetMethod.isAnnotationPresent(RequirePermissions.class)) {
                String[] value = targetMethod.getAnnotation(RequirePermissions.class).value();
                return value.length > 0 ? value : null;
            }
        }
        return null;
    }

    /**
     * 验证payload
     */
    private TokenPayloadVerificationResults validateAuthentication(TokenPayload tokenPayload, String host) {
        // 凭据生成ip地址与当前请求来源不相同 || 没有用户主键 || 用户不存在
        boolean validation = !host.equals(tokenPayload.getLoginIp())
            || tokenPayload.getUserId() == null
            || !userService.userIdExists(tokenPayload.getUserId());
        if (validation) {
            return TokenPayloadVerificationResults.ERROR;
        }
        // 是否过期
        if (tokenPayload.timeout()) {
            // 开启了token过期机制
            LocalDateTime expireTime = tokenPayload.getLoginDatetime().plusSeconds(Constants.DEFAULT_TOKEN_TIMEOUT);
            LocalDateTime now = LocalDateTime.now();
            if (now.isBefore(expireTime)) {
                // 未过期
                return TokenPayloadVerificationResults.SUCCESS;
            } else {
                // 已过期，判断是否可以刷新
                LocalDateTime refreshTime = expireTime.plusSeconds(Constants.DEFAULT_REFRESH_TOKEN_TIMEOUT);
                if (now.isBefore(refreshTime)) {
                    // 可以刷新
                    return TokenPayloadVerificationResults.WAIT_REFRESH;
                } else {
                    // 刷新时间也过期
                    return TokenPayloadVerificationResults.EXPIRED;
                }
            }
        } else {
            // 未开启
            return TokenPayloadVerificationResults.SUCCESS;
        }
    }
}
