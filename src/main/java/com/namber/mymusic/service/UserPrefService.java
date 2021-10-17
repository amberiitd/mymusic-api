package com.namber.mymusic.service;

import com.namber.mymusic.dao.UserPrefRepository;
import com.namber.mymusic.model.PlayList;
import com.namber.mymusic.model.Song;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
@Slf4j
public class UserPrefService {
    @Autowired
    UserPrefRepository userPrefRepository;

    public ArrayList<PlayList> getPlayLists() {
        return userPrefRepository.getAllPlayLists();
    }

    public void createPlayList(String plName) {
        userPrefRepository.createNewPlaylist(plName);
    }

    public void addToPlayList(String plName, String title) {
        userPrefRepository.addToPlayList(plName, title);
    }

    public ArrayList<String> getFavSongs() {
        return userPrefRepository.getFavorites();
    }

    public void addTofavorites(String title) {
        userPrefRepository.addToFavorites(title);
    }

    public ArrayList<String> getPlayHistory() {
        return userPrefRepository.getRecentlyPlayed();
    }

    public void updatePlayHistory(String title) {
        userPrefRepository.addToRecent(title);
    }

    public void deletePlayList(String plName) {
        userPrefRepository.deletePlaylist(plName);
    }

    public void removeFromPlayList(String plName, String title) {
        userPrefRepository.removeFromPlayList(plName, title);
    }

    public void removeFromfavorites(String title) {
        userPrefRepository.removeFavorites(title);
    }
}
