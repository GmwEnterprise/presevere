package cn.gmwenterprise.website.common;

public interface BaseController {

    default AjaxResult ok(String msg, Object data) {
        return AjaxResult.ok(msg, data);
    }

    default AjaxResult ok(Object data) {
        return AjaxResult.ok(data);
    }

    default AjaxResult ok() {
        return AjaxResult.ok();
    }

    default AjaxResult fail(String msg) {
        return AjaxResult.fail(msg);
    }
}
