package com.namber.mymusic.model;

import lombok.Data;
import org.springframework.data.annotation.Id;

import java.util.ArrayList;

@Data
public class UserPreference {
    @Id
    private String id;

    private String userId;
    private ArrayList<PlayList> playLists;
    private ArrayList<String> favorite;
    private ArrayList<String> recent;
}
