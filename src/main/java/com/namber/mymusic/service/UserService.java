package com.namber.mymusic.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class UserService {

    public Long getActiveUserId() {
        return 1L;
    }
}
