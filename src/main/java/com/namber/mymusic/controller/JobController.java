package com.namber.mymusic.controller;

import com.namber.mymusic.job.SongPusher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/job")
public class JobController {
    @Autowired
    SongPusher songPusher;

    @PutMapping("/push-songs")
    public void pushSongsToDB(){
        songPusher.pushSongsToDB();
    }
}
