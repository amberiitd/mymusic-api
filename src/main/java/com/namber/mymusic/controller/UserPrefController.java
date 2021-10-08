package com.namber.mymusic.controller;

import com.namber.mymusic.model.PlayList;
import com.namber.mymusic.model.Song;
import com.namber.mymusic.service.UserPrefService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@RestController
@RequestMapping("/user-pref")
public class UserPrefController {
    @Autowired
    UserPrefService userPrefService;

    @GetMapping("/playlist")
    public ArrayList<PlayList> getPlayLists(){
        return userPrefService.getPlayLists();
    }

    @GetMapping("/create-playlist")
    public void createPlayList(@RequestParam String plName){

        userPrefService.createPlayList(plName);
    }

    @GetMapping("/delete-playlist")
    public void deletePlayList(@RequestParam String plName){

        userPrefService.deletePlayList(plName);
    }

    @PostMapping("/add-to-playlist")
    public void addToPlaylist(@RequestParam String plName, @RequestParam String title){
        userPrefService.addToPlayList(plName, title);
    }

    @PostMapping("/remove-from-playlist")
    public void remoceFromPlaylist(@RequestParam String plName, @RequestParam String title){
        userPrefService.removeFromPlayList(plName, title);
    }

    @GetMapping("/fav")
    public ArrayList<Song> getFavoriteSongs(){
        return userPrefService.getFavSongs();
    }

    @PostMapping("/add-to-fav")
    public void addTofav(@RequestParam String title){
        userPrefService.addTofavorites(title);
    }

    @PostMapping("/remove-from-fav")
    public void removeFromfav(@RequestParam String title){
        userPrefService.removeFromfavorites(title);
    }

    @GetMapping("/recent")
    public ArrayList<Song> getPlayHistory(){
        return userPrefService.getPlayHistory();
    }

    @GetMapping("/update-recent")
    public void updatePlayHistory(@RequestParam String title){
        userPrefService.updatePlayHistory(title);
    }

}
