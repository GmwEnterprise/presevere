package cn.gmwenterprise.website.web.sys;

import cn.gmwenterprise.website.common.BaseController;
import cn.gmwenterprise.website.common.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthorityController implements BaseController {

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/1")
    public ResponseEntity login() {
        return ok("hasRole admin");
    }

    @PreAuthorize("hasRole('ROLE_USER')")
    @GetMapping("/2")
    public ResponseEntity logout() {
        return ok("hasRole user");
    }

    @GetMapping("/3")
    public ResponseEntity register() {
        SecurityContext context = SecurityContextHolder.getContext();
        return ok(context.getAuthentication().getName());
    }
}
