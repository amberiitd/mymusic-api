package com.namber.mymusic.model.request;

import lombok.Data;

import java.util.List;

@Data
public class SongQuery {
    private String title;
    private String album;
    private String artist;
    private String category;
    private List<SongQuery> or;
    private List<SongQuery> and;
    private List<String> in;
    private int limit = -1;
    private int offset = -1;

}
