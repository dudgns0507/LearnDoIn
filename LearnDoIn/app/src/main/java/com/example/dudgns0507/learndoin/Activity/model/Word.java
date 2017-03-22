package com.example.dudgns0507.learndoin.Activity.model;

/**
 * Created by sonbi on 2016-11-07.
 */

public class Word {
    private String wordContent;
    private String meaning;
    private long resolveTime;

    public Word() {

    }

    public Word(String wordContent, String meaning){
        this.wordContent = wordContent;
        this.meaning = meaning;
    }

    public void add(String wordContent, String meaning) {
        this.wordContent = wordContent;
        this.meaning = meaning;
    }

    public String getWordContent() {
        return wordContent;
    }

    public void setWordContent(String wordContent) {
        this.wordContent = wordContent;
    }

    public String getMeaning() {
        return meaning;
    }

    public void setMeaning(String meaning) {
        this.meaning = meaning;
    }

    public long getResolveTime() {
        return resolveTime;
    }

    public void setResolveTime(long resolveTime) {
        this.resolveTime = resolveTime;
    }
}
