package com.example.pmq.base;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Enumeration;

public class RequestInterceptor implements HandlerInterceptor {
    private static final Logger log = LoggerFactory.getLogger(RequestInterceptor.class);

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        Enumeration<String> parameterNames = request.getParameterNames();
        StringBuilder sb = new StringBuilder();
        while (parameterNames.hasMoreElements()) {
            String name = parameterNames.nextElement();
            sb.append(name).append("=").append(request.getParameter(name)).append(". ");
        }
        log.info("入参：{}", sb.toString());
        return true;
    }
}
