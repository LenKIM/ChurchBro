package com.yyy.xxx.churchbro.model;

/**
 * Created by len on 2017. 2. 14..
 */

public class Bible {

    private static final String TAG = Bible.class.getName();

    private static Bible instance = new Bible();

    private String version;
    private int bible;
    private int chapter;
    private int verse;

    private Bible() {
        this.version = "t_kjv";
        this.bible = 1;
        this.chapter = 1;
        this.verse = 1;
    }

    public static Bible getInstance() {
        return instance;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public int getBible() {
        return bible;
    }

    public void setBible(int bible) {
        this.bible = bible;
    }

    public int getChapter() {
        return chapter;
    }

    public void setChapter(int chapter) {
        this.chapter = chapter;
    }

    public int getVerse() {
        return verse;
    }

    public void setVerse(int verse) {
        this.verse = verse;
    }
}
