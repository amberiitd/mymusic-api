package com.namber.mymusic.service;

import com.namber.mymusic.dao.SongRepository;
import com.namber.mymusic.model.Song;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
@Slf4j
public class SongService {
    @Autowired
    SongRepository songRepository;

    public ArrayList<Song> getAllSongsInOrder() {
        log.info("getting all songs from repo...");
        return songRepository.getAllSongs();
    }

    public ArrayList<Song> getSongs(ArrayList<String> titles) {
        return songRepository.getListedSongs(titles);
    }

    public void saveNew(ArrayList<Song> songList) {
        songRepository.save(songList);
    }

    public void saveNew(Song song) {
        songRepository.save(song);
    }

    public Long getTotalCount() {
        return songRepository.getTotalSongsCount();
    }
}
