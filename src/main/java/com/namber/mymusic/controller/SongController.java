package com.namber.mymusic.controller;

import com.namber.mymusic.model.Song;
import com.namber.mymusic.service.SongService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;


import java.util.ArrayList;

@RestController
@RequestMapping(value = "/song")
@Slf4j
public class SongController {

    @Autowired
    SongService songService;

    @GetMapping("/get-all")
    public ArrayList<Song> getAllSongs(){
        log.info("getting all songs...");
        return songService.getAllSongsInOrder();
    }

    @PostMapping("/get-listed-songs")
    public ArrayList<Song> getSongs(@RequestBody ArrayList<String> titles){
        return songService.getSongs(titles);
    }

    @PostMapping("/add-song")
    public void addNewSong(@RequestBody Song song){

        songService.saveNew(song);
    }

    @GetMapping(
        value ="/get-source",
        produces = MediaType.APPLICATION_OCTET_STREAM_VALUE
    )
    public byte[] getSource(@RequestParam String title) throws Exception{
        return IOUtils.toByteArray(songService.getSource(title));
    }

    @GetMapping("/get-songs-count")
    public Long getTotalSongsCount(){
        return songService.getTotalCount();
    }
}
