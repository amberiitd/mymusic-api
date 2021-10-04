package com.namber.mymusic.job;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.google.gson.stream.JsonReader;
import com.namber.mymusic.model.Song;
import com.namber.mymusic.service.SongService;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.FileReader;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class SongPusher {
    Gson gson = new Gson();

    @Autowired
    SongService songService;

    public void pushSongsToDB(){
        JsonReader jsonReader = null;
        try {
            jsonReader = gson.newJsonReader(new FileReader("C:\\Users\\amber\\backend\\mymusic\\src\\main\\resources\\songs.json"));

        }catch (Exception e){
            log.error(e.getMessage());
        }

        JsonElement jsonElement = new JsonParser().parse(jsonReader);
        Type listType = new TypeToken<List<Song>>() {}.getType();

        ArrayList<Song> songsList = gson.fromJson(gson.toJson(jsonElement), listType);

        songService.saveNew(songsList);
    }

//     db.mymusic.insert({"title": "Goodbye My Lover.", "artist": "James Blunt", "album": "Once Upon A Mind", "duration": {"hour": 0, "min": 3, "sec": 57}})
}
