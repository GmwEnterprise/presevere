package cn.gmwenterprise.presevere.common;

import cn.gmwenterprise.presevere.vo.AjaxResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ExceptionManagement {
    private static final Logger log = LoggerFactory.getLogger(ExceptionManagement.class);

    @ExceptionHandler(BusinessException.class)
    public AjaxResult businessExceptionHandler(BusinessException exp) {
        log.error(exp.getMessage());
        return AjaxResult.error(exp.getMessage());
    }
}
