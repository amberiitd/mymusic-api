package com.namber.mymusic.dao;

import com.google.gson.Gson;
import com.mongodb.client.MongoCollection;
import com.namber.mymusic.config.MongoDBConfig;
import com.namber.mymusic.model.Song;
import lombok.extern.slf4j.Slf4j;
import org.bson.Document;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.function.Consumer;

@Repository
@Slf4j
public class SongRepository {

    @Autowired
    MongoDBConfig mongoDBConfig;

    @Autowired
    ModelMapper mapper;

    @Autowired
    @Qualifier("songCollection")
    MongoCollection<Document> collection;

    private Document docParser = new Document();
    private Gson gson = new Gson();

    public ArrayList<Song> getAllSongs() {
        log.info("getting all songs from db...");
        ArrayList<Song> songList = new ArrayList<>();

        collection.find().forEach((Consumer) songDoc -> {
            songList.add(mapper.map(songDoc, Song.class));
        });

        return songList;
    }

    public void save( Song song){
        if (song!= null) {
            collection.insertOne(docParser.parse(gson.toJson(song)));
        }
    }

    public void save( ArrayList<Song> songList){
        ArrayList<Document> docList= new ArrayList<Document>();
        songList.forEach( song ->{
            docList.add(docParser.parse(gson.toJson(song)));
        });
        if (songList != null){
            collection.insertMany(docList);
        }
    }

    public ArrayList<Song> getListedSongs(ArrayList<String> titles) {
        ArrayList<Song> songList = new ArrayList<>();

        if (titles != null) {
            collection.find(new Document("title", new Document("$in", titles))).forEach((Consumer) songDoc -> {
                songList.add(mapper.map(songDoc, Song.class));
            });
        }

        return songList;

    }

    public Long getTotalSongsCount() {
        return 1L;
    }
}
