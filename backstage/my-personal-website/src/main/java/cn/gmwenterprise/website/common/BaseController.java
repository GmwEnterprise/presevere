package cn.gmwenterprise.website.common;

public interface BaseController {

    default ResponseEntity ok(String msg, Object data) {
        return new ResponseEntity(0, msg, data);
    }

    default ResponseEntity ok(Object data) {
        return new ResponseEntity(0, "success", data);
    }

    default ResponseEntity ok() {
        return new ResponseEntity(0, "success", null);
    }

    default ResponseEntity fail(String msg) {
        return new ResponseEntity(-1, msg, null);
    }

    default ResponseEntity fail(int errorCode, String msg) {
        return new ResponseEntity(errorCode, msg, null);
    }
}
