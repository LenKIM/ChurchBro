package com.yyy.xxx.churchbro.model;

/**
 * Created by len on 2017. 2. 14..
 */

public class Bible {

    private static final String TAG = Bible.class.getName();

    private String bible;
    private int chapter;
    private int verse;

    public String getBible() {
        return bible;
    }

    public void setBible(String bible) {
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
