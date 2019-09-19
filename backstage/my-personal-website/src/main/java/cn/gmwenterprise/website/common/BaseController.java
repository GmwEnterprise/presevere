package cn.gmwenterprise.website.common;

public interface BaseController {

    default ResponseEntity ok(String msg, Object data) {
        return new ResponseEntity(ResponseEntity.CODE_SUCCESS, msg, data);
    }

    default ResponseEntity ok(Object data) {
        return ok("success", data);
    }

    default ResponseEntity ok() {
        return ok("success", null);
    }

    default ResponseEntity fail(String msg) {
        return fail(ResponseEntity.CODE_ERROR, msg);
    }

    default ResponseEntity fail(int errorCode, String msg) {
        return new ResponseEntity(errorCode, msg, null);
    }
}
