package me.persevere.project.socialsys.web;

import me.persevere.project.socialsys.domain.SysUser;
import me.persevere.project.socialsys.service.UserService;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping(value = "/user")
public class UserController {
    @Resource
    UserService userService;

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    SysUser getUserById(@PathVariable Long id) {
        return userService.getUserById(id);
    }
}
