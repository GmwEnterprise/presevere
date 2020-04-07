package me.persevere.project.socialsys.web;

import me.persevere.project.socialsys.domain.UserMsg;
import me.persevere.project.socialsys.service.UserService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping(value = "/user")
public class UserController {
    @Resource
    UserService userService;

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    UserMsg getUserById(@PathVariable Long id) {
        return userService.getUserById(id);
    }

    @RequestMapping(value = "/query", method = RequestMethod.GET)
    List<UserMsg> getUserByUsername(@RequestParam String username,
                                    @RequestParam String phone,
                                    @RequestParam String email) {
        UserMsg user = new UserMsg();
        user.setUsername(username);
        user.setPhone(phone);
        user.setEmail(email);
        return userService.getUserList(user);
    }
}
