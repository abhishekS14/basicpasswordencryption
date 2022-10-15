package com.basicpassword.basicpasswordencryption.Controller;

import com.basicpassword.basicpasswordencryption.Model.User;
import com.basicpassword.basicpasswordencryption.Service.EncryptPasswordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.RequestEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/api")
public class LandingPageController {

    @Autowired
    EncryptPasswordService encryptPasswordService;


    @PostMapping("/signUp")
    public void signUp(RequestEntity <User> user) {
        if (user != null) {
            encryptPasswordService.encodePassword(user);
        }
    }

    @PostMapping("/login")
    public HttpStatus login(RequestEntity <User> user) {
        boolean authenticated = false;
        if (user != null) {
            authenticated = encryptPasswordService.isAuthenticated(user);
        }
        if(authenticated) {
            return HttpStatus.OK;
        }
        return HttpStatus.BAD_REQUEST;

    }

}
