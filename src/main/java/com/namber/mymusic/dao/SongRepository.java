package com.namber.mymusic.dao;

import com.google.gson.Gson;
import com.mongodb.client.MongoCollection;
import com.namber.mymusic.config.MongoDBConfig;
import com.namber.mymusic.model.Song;
import lombok.extern.slf4j.Slf4j;
import org.bson.Document;
import org.farng.mp3.AbstractMP3FragmentBody;
import org.farng.mp3.MP3File;
import org.farng.mp3.id3.AbstractID3v2;
import org.farng.mp3.id3.AbstractID3v2Frame;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import java.io.File;
import java.io.FileInputStream;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
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

    public FileInputStream getSongSource(String title) throws  Exception{
        Document songDoc = collection.find(new Document("title", title)).first();
        String src = null;
        if( songDoc != null ){
            src = mapper.map( songDoc , Song.class).getSrc();
        }
        FileInputStream file = new FileInputStream(src);
        return file;
    }

    public byte[] getImage(String title) throws Exception{

        Document songDoc = collection.find(new Document("title", title)).first();
        String src = null;
        if( songDoc != null ){
            src = mapper.map( songDoc , Song.class).getSrc();
        }
//        src= "C:\\Users\\amber\\Downloads\\Arziyan.mp3";
        byte[] data =null;
        MP3File mp3=new MP3File(src);
        AbstractID3v2 id3v2 = mp3.getID3v2Tag();
        if(id3v2 != null){
            Iterator iter = id3v2.getFrameIterator();
            AbstractID3v2Frame apic =null;
            while(iter.hasNext()){
                AbstractID3v2Frame frame= (AbstractID3v2Frame) iter.next();
                if(frame.getIdentifier().startsWith("APIC")){
                    apic = frame;
                }

            }
            if (apic != null){
                AbstractMP3FragmentBody body=apic.getBody();
                data =  (byte[])body.getObject("Picture Data");
                return Arrays.copyOfRange(data, 1, data.length);

//                FileImageOutputStream outputStream = new FileImageOutputStream(new File("C:\\Users\\amber\\Downloads\\img-test.jpg"));
//                outputStream.write(data, 1, data.length -1);
//                outputStream.close();
            }

        }


//        InputStream imgInput = new FileInputStream(src);
//        byte[] img = IOUtils.toByteArray(imgInput);
//        imgInput.close();
//        return img;
//        ByteArrayOutputStream output = new ByteArrayOutputStream();
//
//        Image icn=((ImageIcon) FileSystemView.getFileSystemView().getSystemIcon(new File(src))).getImage();
//        ImageIO.write((BufferedImage) icn, "jpg", output);
//        byte[] data2 = output.toByteArray();
//        icn = icn.getScaledInstance(32,32, Image.SCALE_SMOOTH);
//        FileImageOutputStream outputStream = new FileImageOutputStream(new File("C:\\Users\\amber\\Downloads\\img-test.jpeg"));
//        outputStream.write(data2);
//        outputStream.close();

        File file = new File("C:\\Users\\amber\\Downloads\\img-test2.jpg");
        byte[] data2 = Files.readAllBytes(file.toPath());

        return null;
    }
}
