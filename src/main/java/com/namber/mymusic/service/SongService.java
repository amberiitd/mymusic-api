package com.namber.mymusic.service;

import com.namber.mymusic.dao.SongRepository;
import com.namber.mymusic.model.Song;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.awt.*;
import java.io.File;
import java.io.FileInputStream;
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

    public FileInputStream getSource(String title) throws Exception {
        return songRepository.getSongSource(title);
    }

    public byte[] getThumbnail(String title) throws Exception{
        return songRepository.getImage(title);
    }
}
