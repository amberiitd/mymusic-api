package com.namber.mymusic.controller;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DefaultController {

    @GetMapping("/welcome")
    public String welcome(){
        return "Welcome to MyM";
    }

    @GetMapping("/whoami")
    public Object whoami(@AuthenticationPrincipal Object principal){
        return principal;
    }
}
