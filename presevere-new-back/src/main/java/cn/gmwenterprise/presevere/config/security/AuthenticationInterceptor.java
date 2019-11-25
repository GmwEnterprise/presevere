package cn.gmwenterprise.presevere.config.security;

import cn.gmwenterprise.presevere.common.Constants;
import cn.gmwenterprise.presevere.common.TokenHelper;
import cn.gmwenterprise.presevere.domain.SysUser;
import cn.gmwenterprise.presevere.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.time.LocalDateTime;

@Component
public class AuthenticationInterceptor implements HandlerInterceptor {
    private static final Logger log = LoggerFactory.getLogger(AuthenticationInterceptor.class);

    @Resource
    UserService userService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        log.info("AuthenticationInterceptor.preHandle");
        String token = request.getHeader("Authorization");
        if (!StringUtils.isEmpty(token)) {
            log.info("Authorization: [{}]", token);
            Authentication authentication = TokenHelper.parseToken(token, Authentication.class);
            if (validateAuthentication(authentication, request)) {
                // token验证通过后，AuthorizationHolder中也有了值
                Authorization authorization = AuthorizationHolder.get();
                authorization.setAuthentication(authentication);
                authorization.setToken(token);
            }
        }
        return true;
    }

    /**
     * 验证token
     */
    private boolean validateAuthentication(Authentication authentication, HttpServletRequest request) {
        // 没有凭据或无法解析
        if (authentication == null) {
            return false;
        }

        // 凭据生成ip地址与当前请求来源不相同
        if (!request.getRemoteHost().equals(authentication.getLoginIp())) {
            return false;
        }

        // TODO 判断客户端属于哪个Platform

        // 判断用户信息
        Authorization authorization = new Authorization();
        if (authentication.getUserId() != null) {
            SysUser currentUser = userService.getUserById(authentication.getUserId());
            if (currentUser == null) {
                return false;
            }
            authorization.setCurrentUser(currentUser);
        } else {
            return false;
        }

        // 如果timeout值为0就不过期
        Long timeout = authentication.getTimeout();
        log.info("timeout: {}", timeout);
        if (timeout > 0L) {
            // 设置了过期时间
            LocalDateTime expireTime = authentication.getLoginDatetime().plusMinutes(timeout);
            LocalDateTime now = LocalDateTime.now();
            if (!expireTime.isAfter(now)) {
                // 过期了
                long refreshTimeout = timeout + Constants.DEFAULT_REFRESH_TOKEN_TIMEOUT;
                LocalDateTime refreshTime = authentication.getLoginDatetime().plusMinutes(refreshTimeout);
                if (refreshTime.isAfter(now)) {
                    // 设置刷新token标识符
                    Authentication payload = new Authentication(
                        LocalDateTime.now(), request.getRemoteHost(), authorization.getCurrentUser().getId(),
                        Authentication.Platform.BROWSER, Constants.DEFAULT_TOKEN_TIMEOUT
                    );
                    request.setAttribute(Constants.TOKEN_REFRESH_KEY, TokenHelper.generateToken(payload));
                } else {
                    return false;
                }
            }
        }

        AuthorizationHolder.set(authorization);
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        log.info("AuthenticationInterceptor.postHandle");
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        log.info("AuthenticationInterceptor.afterCompletion");
        AuthorizationHolder.remove();
    }
}
