package cn.gmwenterprise.website.web.sys;

import cn.gmwenterprise.website.common.BaseController;
import cn.gmwenterprise.website.common.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthorityController implements BaseController {

    @PostMapping("/login")
    public ResponseEntity login() {
        return ok();
    }

    @PostMapping("/logout")
    public ResponseEntity logout() {
        return ok();
    }

    @PostMapping("/reg")
    public ResponseEntity register() {
        return ok();
    }
}
