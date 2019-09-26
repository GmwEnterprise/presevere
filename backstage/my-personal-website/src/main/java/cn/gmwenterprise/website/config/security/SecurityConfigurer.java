package cn.gmwenterprise.website.config.security;

import cn.gmwenterprise.website.common.AjaxResult;
import cn.gmwenterprise.website.vo.AuthResult;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.Maps;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;

import static cn.gmwenterprise.website.config.security.JwtAuthenticationFilter.KEY_USERNAME;

@Slf4j
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfigurer extends WebSecurityConfigurerAdapter {

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/static/**");
    }

    @Resource
    ObjectMapper objectMapper;

    @Resource
    JwtAuthenticationFilter jwtAuthenticationFilter;

    @Resource
    UserDetailsService jwtUserDetailsService;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(jwtUserDetailsService)
            .passwordEncoder(passwordEncoder());
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
            // 禁用csrf
            .csrf().disable()
            // 禁用session
            .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            // 拒绝访问处理
            .and().exceptionHandling()
            .authenticationEntryPoint((request, response, authException) -> {
                log.error(authException.getMessage(), authException);
                AjaxResult failResp = AjaxResult.fail(AjaxResult.CODE_NEED_AUTH, authException.getMessage());
                getWriter(response).write(objectMapper.writeValueAsString(failResp));
            })
            .accessDeniedHandler((request, response, accessDeniedException) -> {
                log.error(accessDeniedException.getMessage(), accessDeniedException);
                AjaxResult failResp = AjaxResult.fail(AjaxResult.CODE_ACCESS_DENY, accessDeniedException.getMessage());
                getWriter(response).write(objectMapper.writeValueAsString(failResp));
            })
            // 接口权限由注解来处理
            .and().authorizeRequests().anyRequest().permitAll()
            // 开启登录
            .and().formLogin().loginProcessingUrl("/login")
            // 登录成功
            .successHandler((request, response, authentication) -> {
                SecurityContextHolder.getContext().setAuthentication(authentication);
                Map<String, Object> payload = Maps.newHashMap();
                User user = (User) authentication.getPrincipal();
                payload.put(KEY_USERNAME, user.getUsername());
                String token = JwtUtils.createToken(payload);
                AjaxResult returnData = AjaxResult.ok(new AuthResult(user.getUser(), token));
                getWriter(response).write(objectMapper.writeValueAsString(returnData));
            })
            // 登录失败
            .failureHandler((request, response, exception) -> {
                log.error(exception.getMessage(), exception);
                AjaxResult failResp = AjaxResult.fail(AjaxResult.CODE_LOGIN_FAILURE, exception.getMessage());
                getWriter(response).write(objectMapper.writeValueAsString(failResp));
            })
            // 添加自定义过滤器
            .and().addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
    }

    private PrintWriter getWriter(HttpServletResponse response) throws IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        return response.getWriter();
    }
}