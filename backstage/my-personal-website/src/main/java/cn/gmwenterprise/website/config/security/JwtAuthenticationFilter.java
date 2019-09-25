package cn.gmwenterprise.website.config.security;

import cn.gmwenterprise.website.domain.SysUser;
import cn.gmwenterprise.website.service.sys.UserTokenService;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.annotation.Resource;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private static final String KEY_HEAD_PARAM = "Authorization";

    private static final String KEY_USER_ID = "userId";

    @Resource
    private UserTokenService userTokenService;

    @Override
    protected void doFilterInternal(
        HttpServletRequest request, HttpServletResponse response, FilterChain filterChain
    ) throws ServletException, IOException {
        String authorization = request.getHeader(KEY_HEAD_PARAM);
        Map<String, Object> authMap = JwtUtils.parseToken(authorization);
        if (authMap != null && authMap.containsKey(KEY_USER_ID)) {
            // token验证通过
            Integer userId = (Integer) authMap.get(KEY_USER_ID);
            UserDetails userDetails = userTokenService.generateUser(userId);
            UserToken userToken = new UserToken(userDetails);
            SecurityContextHolder.getContext().setAuthentication(userToken);
        }
    }
}
