package com.namber.mymusic.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.spi.service.contexts.SecurityContextBuilder;

@RestController
@Slf4j
public class DefaultController {

    @GetMapping("/welcome")
    public String welcome(){
        return "Welcome to MyM";
    }

    @GetMapping("/whoami")
    public Object whoami(@AuthenticationPrincipal Object principal){
//        log.info(SecurityContextHolder.getContext().getAuthentication().toString());
        return principal;
    }
}
