package cn.gmwenterprise.website.config.security;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
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

    public static final String KEY_USERNAME = "username";

    @Resource
    UserDetailsService jwtUserDetailsService;

    @Override
    protected void doFilterInternal(
        HttpServletRequest request, HttpServletResponse response, FilterChain filterChain
    ) throws ServletException, IOException {
        String authorization = request.getHeader(KEY_HEAD_PARAM);
        Map<String, Object> authMap = JwtUtils.parseToken(authorization);
        if (authMap != null && authMap.containsKey(KEY_USERNAME)) {
            // token验证通过
            String username = (String) authMap.get(KEY_USERNAME);
            UserDetails userDetails = jwtUserDetailsService.loadUserByUsername(username);
            if (userDetails != null) {
                UsernamePasswordAuthenticationToken authenticationUser =
                    new UsernamePasswordAuthenticationToken(
                        userDetails, null, userDetails.getAuthorities()
                    );
                // AuthenticationUser authenticationUser = new AuthenticationUser(userDetails);
                authenticationUser
                    .setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authenticationUser);
            }
        }
        doFilter(request, response, filterChain);
    }
}
