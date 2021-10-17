package com.namber.mymusic.model;

import lombok.Data;
import org.springframework.data.annotation.Id;

@Data
public class Song{
    @Id
    private int id;
    private String title;
    private String artist;
    private String album;
    private Duration duration;
    private String src;
}
