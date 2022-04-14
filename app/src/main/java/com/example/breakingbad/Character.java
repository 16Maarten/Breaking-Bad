package com.example.breakingbad;

import android.util.Log;

import java.io.Serializable;
import java.util.ArrayList;

//domein klasse
public class Character implements Serializable {
    //attributen
    private final String TAG = getClass().getSimpleName();
    private int charId;
    private String characterName;
    private String birthday;
    private ArrayList<String> occupation;
    private String img;
    private String status;
    private String nickname;
    private ArrayList<Integer> appearance;

    //constructor
    public Character(int charId, String characterName, String birthday, ArrayList<String> occupation, String img, String status, String nickname, ArrayList<Integer> appearance) {
        Log.d(TAG, "Constructor aangeroepen");
        this.charId = charId;
        this.characterName = characterName;
        this.birthday = birthday;
        this.occupation = occupation;
        this.img = img;
        this.status = status;
        this.nickname = nickname;
        this.appearance = appearance;
    }

    // Getters
    public int getCharId() {
        return charId;
    }

    public String getCharacterName() {
        return characterName;
    }

    public String getBirthday() {
        return birthday;
    }

    public ArrayList<String> getOccupation() {
        return occupation;
    }

    public String getImg() {
        return img;
    }

    public String getStatus() {
        return status;
    }

    public String getNickname() {
        return nickname;
    }

    public ArrayList<Integer> getAppearance() {
        return appearance;
    }
}
