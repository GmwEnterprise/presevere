package com.example.learn.springmvcreturnval;

import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

@ControllerAdvice
public class DealReturnval implements ResponseBodyAdvice {

    // FIXME 当前的用法是错误的，假如controller返回一个String，这里似乎不能将其封装为统一返回对象

    @Override
    public boolean supports(MethodParameter returnType, Class converterType) {
        return true;
    }

    @Override
    public Object beforeBodyWrite(Object body,
                                  MethodParameter returnType,
                                  MediaType selectedContentType,
                                  Class selectedConverterType,
                                  ServerHttpRequest request,
                                  ServerHttpResponse response) {
        System.out.println("tet");

        HttpResponse httpResponse = new HttpResponse();
        httpResponse.setCode(200);
        httpResponse.setMessage("Success");
        httpResponse.setData(body);
        return httpResponse;
    }
}
