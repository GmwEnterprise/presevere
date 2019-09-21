package cn.gmwenterprise.website.config;

import cn.gmwenterprise.website.common.ResponseEntity;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.annotation.Resource;
import javax.servlet.ServletResponse;
import java.io.IOException;

@Slf4j
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Resource
    private ObjectMapper objectMapper;
    @Resource(name = "customizeUserDetailsService")
    private UserDetailsService userDetailsService;

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/static/**");
    }

    /**
     * 身份验证管理生成器
     *
     * @param auth auth
     * @throws Exception exp
     */
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        // 定义密码编解码工具
        PasswordEncoder passwordEncoder = new PasswordEncoder() {
            @Override
            public String encode(CharSequence rawPassword) {
                return rawPassword.toString();
            }

            @Override
            public boolean matches(CharSequence rawPassword, String encodedPassword) {
                return rawPassword.equals(encodedPassword);
            }
        };
        // 自定义鉴权
        auth.authenticationProvider(new AuthenticationProvider() {
            @Override
            public Authentication authenticate(Authentication authentication) throws AuthenticationException {
                // 获取用户名与密码
                String username = (String) authentication.getPrincipal();
                String password = (String) authentication.getCredentials();

                // 去掉头尾空格
                username = username.trim();
                password = password.trim();

                // 获取用户数据
                UserDetails user = userDetailsService.loadUserByUsername(username);
                if (!passwordEncoder.matches(password, user.getPassword())) {
                    throw new BadCredentialsException("密码错误");
                }

                // 存储用户信息

                return new UsernamePasswordAuthenticationToken();
            }

            @Override
            public boolean supports(Class<?> authentication) {
                return true;
            }
        });
    }

    @Bean
    public PasswordEncoder encoder() {
        return new PasswordEncoder() {
            @Override
            public String encode(CharSequence rawPassword) {
                return rawPassword.toString();
            }

            @Override
            public boolean matches(CharSequence rawPassword, String encodedPassword) {
                return rawPassword.equals(encodedPassword);
            }
        };
    }

    /**
     * HTTP请求安全处理
     * 自定义formLogin实现前后端分离的登录
     *
     * @param http http
     * @throws Exception exp
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
            // 关闭csrf和session
            .csrf().disable().sessionManagement()
            .sessionCreationPolicy(SessionCreationPolicy.STATELESS)

            .and().exceptionHandling()

            // 匿名用户访问无权限资源时异常处理
            .authenticationEntryPoint((request, response, e) -> writeJsonResponse(
                response, ResponseEntity.of(ResponseEntity.CODE_ACCESS_DENY, "游客无权访问")))

            // 登录用户无权访问时异常处理
            .accessDeniedHandler((request, response, e) -> writeJsonResponse(
                response, ResponseEntity.of(ResponseEntity.CODE_ACCESS_DENY, "用户无权访问")))

            // TODO 动态权限验证
            // .and().authorizeRequests()

            // 开启登陆
            .and().formLogin()

            // 自定义登陆成功处理器
            .successHandler((request, response, authentication) -> writeJsonResponse(
                response, ResponseEntity.of(ResponseEntity.CODE_SUCCESS, "登录成功", authentication.getPrincipal())))

            // 自定义登陆失败处理器
            .failureHandler((request, response, error) -> writeJsonResponse(
                response, ResponseEntity.of(ResponseEntity.CODE_LOGIN_FAILURE, "登陆失败: " + error.getMessage())
            ));
    }

    private void writeJsonResponse(ServletResponse response, Object obj) throws IOException {
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json");
        response.getWriter().write(objectMapper.writeValueAsString(obj));
    }
}
