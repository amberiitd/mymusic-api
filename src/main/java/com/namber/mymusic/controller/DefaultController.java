package com.namber.mymusic.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DefaultController {
    @GetMapping("")
    public String test(){
        return "MyM API works";
    }

    @GetMapping("/welcome")
    public String welcome(){
        return "Welcome to MyM";
    }
}
