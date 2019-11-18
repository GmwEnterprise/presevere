package cn.gmwenterprise.presevere.config.interceptor;

import cn.gmwenterprise.presevere.common.AuthorizationHolder;
import cn.gmwenterprise.presevere.common.TokenHelper;
import cn.gmwenterprise.presevere.domain.SysUser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class AuthenticationInterceptor implements HandlerInterceptor {
    private static final Logger log = LoggerFactory.getLogger(AuthenticationInterceptor.class);

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        log.info("preHandle");
        String token = request.getHeader("Authorization");
        if (!StringUtils.isEmpty(token)) {
            SysUser currentUser = TokenHelper.parseToken(token, SysUser.class);
            if (currentUser != null) {
                AuthorizationHolder.setCurrentUser(currentUser);
            }
        }
        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        log.info("afterCompletion");
        AuthorizationHolder.removeCurrentUser();
    }
}
