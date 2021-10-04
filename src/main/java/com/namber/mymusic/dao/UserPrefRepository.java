package com.namber.mymusic.dao;

import com.google.gson.Gson;
import com.mongodb.client.MongoCollection;
import com.namber.mymusic.common.constant.AttributeConstants;
import com.namber.mymusic.common.constant.MongoQueryOperator;
import com.namber.mymusic.config.MongoDBConfig;
import com.namber.mymusic.model.PlayList;
import com.namber.mymusic.model.Song;
import com.namber.mymusic.model.UserPreference;
import com.namber.mymusic.service.SongService;
import com.namber.mymusic.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.bson.Document;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.stream.Collectors;

@Repository
@Slf4j
public class UserPrefRepository {
    @Autowired
    MongoDBConfig mongoDBConfig;

    @Autowired
    SongService songService;

    @Autowired
    UserService userService;

    @Autowired
    @Qualifier("userPrefCollection")
    MongoCollection<Document> collection;

    @Autowired
    ModelMapper mapper;

    private Document docParser = new Document();
    private Gson gson = new Gson();

    public ArrayList<PlayList> getAllPlayLists(){
        return getUserPref().getPlayLists();
    }

    public void createNewPlaylist(String plName){
        if (plName == null){
            return;
        }
        ArrayList<PlayList> playLists = getUserPref().getPlayLists();

        if (playLists == null){
            playLists = new ArrayList();
        }else if(playLists.stream().filter(pl -> plName.equals(pl.getPlName())).collect(Collectors.toList()).size() > 0){
            return;
        }
        PlayList newPlayList= new PlayList();
        newPlayList.setPlName(plName);

        playLists.add(newPlayList);

        ArrayList<Document> docList = new ArrayList<>();
        playLists.forEach( playList ->{
            docList.add(docParser.parse(gson.toJson(playList)));
        });
        collection
                .updateOne(
                        new Document(AttributeConstants.USER_ID, userService.getActiveUserId()),
                        new Document(MongoQueryOperator.SET, new Document(AttributeConstants.PLAY_LISTS, docList))
                );
    }

    public void deletePlaylist(String plName){
        if (plName == null){
            return;
        }
        ArrayList<PlayList> playLists = getUserPref().getPlayLists();

        if (playLists != null){
            playLists = (ArrayList<PlayList>) playLists.stream().filter(pl -> !plName.equals(pl.getPlName())).collect(Collectors.toList());
        }

        ArrayList<Document> docList = new ArrayList<>();
        playLists.forEach( playList ->{
            docList.add(docParser.parse(gson.toJson(playList)));
        });
        collection
                .updateOne(
                        new Document(AttributeConstants.USER_ID, userService.getActiveUserId()),
                        new Document(MongoQueryOperator.SET, new Document(AttributeConstants.PLAY_LISTS, docList))
                );
    }

    public void addToPlayList(String plName, String title){
        if (plName == null || title == null){
            return;
        }
        ArrayList<PlayList> playLists = getUserPref().getPlayLists();

        if (playLists == null){
            log.info("playList {} does not exit!", plName);
            return;
        }

        PlayList pl = playLists.stream().filter( playList -> plName.equals(playList.getPlName())).collect(Collectors.toList()).get(0);
        if (pl == null){
            log.info("playList {} does not exit!", plName);
            return;
        }

        if (pl.getSongs()!=null) {
            pl.getSongs().add(title);
        }else{
            ArrayList<String> songList= new ArrayList<>();
            songList.add(title);
            pl.setSongs(songList);
        }

        ArrayList<Document> docList = new ArrayList<>();
        playLists.forEach( playList ->{
            docList.add(docParser.parse(gson.toJson(playList)));
        });
        collection
                .updateOne(
                        new Document(AttributeConstants.USER_ID, userService.getActiveUserId()),
                        new Document(MongoQueryOperator.SET, new Document(AttributeConstants.PLAY_LISTS, docList))
                );
    }

    public void removeFromPlayList(String plName, String title){
        ArrayList<PlayList> playLists = getUserPref().getPlayLists();

        if (plName == null || title == null || playLists == null){
            return;
        }

        PlayList pl = playLists.stream().filter( playList -> plName.equals(playList.getPlName())).collect(Collectors.toList()).get(0);
        if (pl == null){
            log.info("playList {} does not exit!", plName);
            return;
        }

        if (pl.getSongs()!=null) {
            pl.setSongs((ArrayList<String>) pl.getSongs().stream().filter(song -> !title.equals(song)).collect(Collectors.toList()));
        }

        ArrayList<Document> docList = new ArrayList<>();
        playLists.forEach( playList ->{
            docList.add(docParser.parse(gson.toJson(playList)));
        });
        collection
                .updateOne(
                        new Document(AttributeConstants.USER_ID, userService.getActiveUserId()),
                        new Document(MongoQueryOperator.SET, new Document(AttributeConstants.PLAY_LISTS, docList))
                );
    }

    public ArrayList<Song> getFavorites(){
        ArrayList<String> favSongTitles = getUserPref().getFavorite();

        return songService.getSongs(favSongTitles);
    }

    public void addToFavorites(String title){
        if (title == null){
            return;
        }
        ArrayList<String> favList = getUserPref().getFavorite();

        if (favList == null){
            favList = new ArrayList();
        }
        if (favList.stream().filter( songTitle -> title.equals(songTitle)).collect(Collectors.toList()).size() == 0) {
            favList.add(title);
        }
        collection
                .updateOne(
                        new Document(AttributeConstants.USER_ID, userService.getActiveUserId()),
                        new Document(MongoQueryOperator.SET, new Document(AttributeConstants.FAVORITE, favList))
                );
    }
    public void removeFavorites(String title){
        ArrayList<String> favList = getUserPref().getFavorite();

        if (title == null || favList == null){
            return;
        }
        favList = (ArrayList<String>) favList.stream().filter(songTitle -> !title.equals(songTitle)).collect(Collectors.toList());
        collection
                .updateOne(
                        new Document(AttributeConstants.USER_ID, userService.getActiveUserId()),
                        new Document(MongoQueryOperator.SET, new Document(AttributeConstants.FAVORITE, favList))
                );
    }

    public ArrayList<Song> getRecentlyPlayed(){
        ArrayList<String> favSongTitles = getUserPref().getRecent();

        return songService.getSongs(favSongTitles);
    }

    public void addToRecent(String title){
        ArrayList recentList = getUserPref().getRecent();

        if (recentList == null){
            recentList = new ArrayList();
        }
        if (!title.equals(recentList.get(recentList.size()-1))){
            recentList.add(title);
        }
        collection
                .updateOne(
                        new Document(AttributeConstants.USER_ID, userService.getActiveUserId()),
                        new Document(MongoQueryOperator.SET, new Document(AttributeConstants.RECENT, recentList))
                );
    }

    private UserPreference getUserPref(){
        return mapper.map(collection.find(new Document().append(AttributeConstants.USER_ID, userService.getActiveUserId())).first(), UserPreference.class);
    }

}
