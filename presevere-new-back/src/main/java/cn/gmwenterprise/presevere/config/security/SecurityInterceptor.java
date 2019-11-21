package cn.gmwenterprise.presevere.config.security;

import cn.gmwenterprise.presevere.common.AuthRequire;
import cn.gmwenterprise.presevere.common.Authorization;
import cn.gmwenterprise.presevere.common.AuthorizationHolder;
import cn.gmwenterprise.presevere.common.Constants;
import cn.gmwenterprise.presevere.domain.SysPermission;
import cn.gmwenterprise.presevere.service.UserService;
import cn.gmwenterprise.presevere.vo.AjaxResult;
import cn.gmwenterprise.presevere.vo.Res;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Method;
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
        String[] permissions = getPermissions(handler);
        if (permissions != null && permissions.length > 0) {
            log.info("进入安全拦截器, 请求URL = [{}]", request.getRequestURL());
            Authorization authorization = AuthorizationHolder.get();
            if (authorization != null) {
                List<SysPermission> permissionList = userService.getUserPermissionsByUserId(authorization.getCurrentUser().getId());
                if (permissionList != null && permissionList.size() >= permissions.length) {
                    boolean hasPermission = permissionList.stream()
                        .map(SysPermission::getPermission)
                        .collect(Collectors.toList())
                        .containsAll(Arrays.asList(permissions));
                    if (hasPermission) {
                        return true;
                    }
                }
                return noAccess(response, "当前用户无访问权限", Res.UNAUTHORIZED);
            }
            return noAccess(response, "禁止访问，需要登陆", Res.LOGIN_REQUIRED);
        }
        return true;
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

    private String[] getPermissions(Object handler) {
        if (handler instanceof HandlerMethod) {
            Method targetMethod = ((HandlerMethod) handler).getMethod();
            if (targetMethod.isAnnotationPresent(AuthRequire.class)) {
                String[] value = targetMethod.getAnnotation(AuthRequire.class).value();
                return value.length > 0 ? value : null;
            }
        }
        return null;
    }
}
