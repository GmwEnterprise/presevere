//package cn.gmwenterprise.presevere.config.security;
//
//import cn.gmwenterprise.presevere.common.Constants;
//import cn.gmwenterprise.presevere.vo.AjaxResult;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.core.MethodParameter;
//import org.springframework.http.MediaType;
//import org.springframework.http.server.ServerHttpRequest;
//import org.springframework.http.server.ServerHttpResponse;
//import org.springframework.http.server.ServletServerHttpRequest;
//import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;
//
///**
// * 由于修改了token刷新的方式 该接口弃用
// */
////@ControllerAdvice
//public class RefreshTokenResponseBodyAdvice implements ResponseBodyAdvice<AjaxResult> {
//    private static final Logger log = LoggerFactory.getLogger(RefreshTokenResponseBodyAdvice.class);
//
//    @Override
//    public boolean supports(MethodParameter returnType, Class converterType) {
//        // 返回true则执行beforeBodyWrite; false则不执行
//        return returnType.hasMethodAnnotation(AuthRequire.class);
//    }
//
//    @Override
//    public AjaxResult beforeBodyWrite(
//        AjaxResult body, MethodParameter returnType, MediaType selectedContentType,
//        Class selectedConverterType, ServerHttpRequest request, ServerHttpResponse response
//    ) {
//        ServletServerHttpRequest httpRequest = (ServletServerHttpRequest) request;
//        Object tokenRefreshValue = httpRequest.getServletRequest().getAttribute(Constants.TOKEN_REFRESH_KEY);
//        // instanceof String 本身具备鉴别isNull的能力
////        if (tokenRefreshValue instanceof String) {
////            body.setTokenRefresh(true);
////            body.setToken(((String) tokenRefreshValue));
////        }
//        return body;
//    }
//}
