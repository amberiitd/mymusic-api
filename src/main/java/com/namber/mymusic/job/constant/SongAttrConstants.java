package com.namber.mymusic.job.constant;

import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SongAttrConstants {
    public static String ARTIST = "artist";
    public static String ALBUM = "album";
    public static String TITLE = "title";
    public static String YEAR = "year";
    public static String ALBUM_ARTIST = "albumArtist";
    public static String GENRE = "genre";



    public static List<String> ARTISTS =Arrays.asList("artist", "xmpDM:artist", "dc:creator","xmpDM:albumArtist", "Author");
    public static List<String> ALBUMS =Arrays.asList("album", "xmpDM:album", "dc:album");
    public static List<String> TITLES =Arrays.asList("title", "xmpDM:title", "dc:title");
    public static List<String> YEARS =Arrays.asList("xmpDM:releaseDate");
    public static List<String> ALBUM_ARTISTS =Arrays.asList("xmpDM:albumArtist");
    public static List<String> GENRES =Arrays.asList("genre","dc:genre","xmpDM:genre");


    public static Map<String, List<String>> ATTR_MAP = new HashMap<>();

    static{
        ATTR_MAP.put(ARTIST, ARTISTS);
        ATTR_MAP.put(ALBUM, ALBUMS);
        ATTR_MAP.put(TITLE, TITLES);
        ATTR_MAP.put(YEAR, YEARS);
        ATTR_MAP.put(ALBUM_ARTIST, ALBUM_ARTISTS);
        ATTR_MAP.put(GENRE, GENRES);

    }

}
