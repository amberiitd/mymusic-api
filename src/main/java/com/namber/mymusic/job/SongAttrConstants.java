package com.namber.mymusic.job;

import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SongAttrConstants {
    public static String ARTIST = "artist";
    public static String ALBUM = "album";
    public static String TITLE = "title";

    public static List<String> ARTISTS =Arrays.asList("artist", "xmpDM:artist", "dc:creator","xmpDM:albumArtist", "Author");
    public static List<String> ALBUMS =Arrays.asList("album", "xmpDM:album", "dc:album");
    public static List<String> TITLES =Arrays.asList("title", "xmpDM:title", "dc:title");
    public static Map<String, List<String>> ATTR_MAP = new HashMap<>();

    static{
        ATTR_MAP.put(ARTIST, ARTISTS);
        ATTR_MAP.put(ALBUM, ALBUMS);
        ATTR_MAP.put(TITLE, TITLES);
    }

}
