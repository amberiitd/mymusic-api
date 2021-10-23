package com.namber.mymusic.job;

import com.google.gson.Gson;
import com.namber.mymusic.job.constant.SongAttrConstants;
import com.namber.mymusic.model.Song;
import com.namber.mymusic.service.SongService;
import lombok.extern.slf4j.Slf4j;
import org.apache.tika.metadata.Metadata;
import org.apache.tika.parser.ParseContext;
import org.apache.tika.parser.Parser;
import org.apache.tika.parser.mp3.Mp3Parser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.xml.sax.helpers.DefaultHandler;

import java.io.*;
import java.nio.file.Files;

@Service
@Slf4j
public class SongPusher {
    Gson gson = new Gson();

    @Value("${resource.rootPath}")
    private String resourcePath;

    @Value("${inventory.rootPath}")
    private String inventoryPath;

    @Autowired
    SongService songService;

    public void pushSongsToDB() throws Exception{

        // get songs from resource
        File resourceFolder = new File (resourcePath);
        File[] songFiles = resourceFolder.listFiles();

        // for each songs
        for( File song : songFiles){
            try{
                // get details
                Song songData = getSong(song);
                // push songs data to song db
                songService.saveNew(songData);
                // push to inventory
                String targetPath = inventoryPath + "\\" + song.getName();
                OutputStream target = new FileOutputStream(targetPath);
                Files.copy(song.toPath(), target);
                Files.delete(song.toPath());
                // log
                log.info("transfered song: {} to {}", song.getAbsolutePath(), targetPath);
            }catch (Exception e) {
                log.error(e.getMessage());
                e.printStackTrace();
            }
        }

//        JsonReader jsonReader = null;
//        File file = new File("");
//        Path path = Paths.get(file.getPath());
//        BasicFileAttributes attr = Files.readAttributes(path , BasicFileAttributes.class);
//        attr.fileKey()
//        try {
//            jsonReader = gson.newJsonReader(new FileReader("C:\\Users\\amber\\backend\\mymusic\\src\\main\\resources\\songs.json"));
//
//        }catch (Exception e){
//            log.error(e.getMessage());
//        }
//
//        JsonElement jsonElement = new JsonParser().parse(jsonReader);
//        Type listType = new TypeToken<List<Song>>() {}.getType();
//
//        ArrayList<Song> songsList = gson.fromJson(gson.toJson(jsonElement), listType);

//        songService.saveNew(songsList);
    }

    private Song getSong(File song) throws Exception{
        Metadata metadata = new Metadata();
        Parser parser = new Mp3Parser();
        ParseContext parseContext = new ParseContext();
        DefaultHandler contentHandler = new DefaultHandler();
        InputStream input = new FileInputStream(song);

        parser.parse(input, contentHandler, metadata, parseContext);
        input.close();

//        log.info("MetaData: {}", metadata.toString());

        Song songData = new Song();
        songData.setArtist(getFromMetadata(metadata, SongAttrConstants.ARTIST));
        songData.setAlbum(getFromMetadata(metadata, SongAttrConstants.ALBUM));
        songData.setTitle(formatTitle(getFromMetadata(metadata, SongAttrConstants.TITLE)));
        songData.setGenre(getFromMetadata(metadata, SongAttrConstants.GENRE));
        songData.setAlbumArtist(getFromMetadata(metadata, SongAttrConstants.ALBUM_ARTIST));
        songData.setYear(getFromMetadata(metadata, SongAttrConstants.YEAR));
        songData.setSrc(inventoryPath + "\\" + song.getName());
        return songData;
    }

    private String formatTitle(String fromMetadata) {
        return fromMetadata.split("-")[0];
    }

    private String getFromMetadata(Metadata metadata, String valKey){
        String value = null;
        for (String key: SongAttrConstants.ATTR_MAP.get(valKey)){
            value = metadata.get(key);
            if (value != null){
                return value;
            }
        }
        return valKey+ "Unknown";
    }

//     db.mymusic.insert({"title": "Goodbye My Lover.", "artist": "James Blunt", "album": "Once Upon A Mind", "duration": {"hour": 0, "min": 3, "sec": 57}})
}
