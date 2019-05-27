package com.zthulj.blog.api;

import com.zthulj.blog.referential.SecurityConfig;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(origins = "*")
public class UserApi {

    @PostMapping(value = SecurityConfig.SECURED_API + "/user/check_token")
    public String get() {
        return "OK";
    }

}
