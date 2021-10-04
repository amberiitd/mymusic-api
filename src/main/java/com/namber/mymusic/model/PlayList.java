package com.namber.mymusic.model;

import lombok.Data;

import java.util.ArrayList;

@Data
public class PlayList{
    private String plName;
    private ArrayList<String> songs;
}
