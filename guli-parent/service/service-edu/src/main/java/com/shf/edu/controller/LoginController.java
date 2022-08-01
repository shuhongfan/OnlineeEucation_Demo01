package com.shf.edu.controller;

import com.shf.commonutils.R;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/eduservice/user")
@CrossOrigin
public class LoginController {

    @PostMapping("login")
    public R login() {
        return R.ok().data("token", "admin");
    }

    @GetMapping("info")
    public R info() {
        return R.ok()
                .data("avatar", "http://oss.shuhongfan.cf/shf.jpg")
                .data("name", "[admin]")
                .data("roles", "[admin]");
    }
}
