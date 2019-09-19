package cn.gmwenterprise.website.config;

import cn.gmwenterprise.website.common.ResponseEntity;
import cn.gmwenterprise.website.common.SpringContext;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.annotation.PostConstruct;
import javax.servlet.ServletResponse;
import java.io.IOException;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    private ObjectMapper objectMapper;

    @PostConstruct
    public void initBeans() {
        objectMapper = SpringContext.getBean(ObjectMapper.class);
    }

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
        // TODO authenticationProvider的配置
        // auth.authenticationProvider(authenticationProvider);
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
                response, ResponseEntity.of(ResponseEntity.CODE_LOGIN_FAILURE, "登陆失败: null")
            ));
    }

    private void writeJsonResponse(ServletResponse response, Object obj) throws IOException {
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json");
        response.getWriter().write(objectMapper.writeValueAsString(obj));
    }
}
