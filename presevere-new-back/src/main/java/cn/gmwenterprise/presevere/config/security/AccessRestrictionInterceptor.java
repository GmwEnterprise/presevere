package cn.gmwenterprise.presevere.config.security;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * 限制单个请求的访问次数，不允许访问期间重复访问
 */
@Component
public class AccessRestrictionInterceptor implements HandlerInterceptor {
    private static final Logger log = LoggerFactory.getLogger(AccessRestrictionInterceptor.class);

    private ConcurrentMap<String, Integer> requestUrlTimes = new ConcurrentHashMap<>();

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        log.info("访问限制拦截器检测中...");
        String url = request.getRequestURL().toString();
        if (requestUrlTimes.containsKey(url)) {
            return false;
        }
        requestUrlTimes.put(url, 1);
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        requestUrlTimes.remove(request.getRequestURL().toString());
    }
}
