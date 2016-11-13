package com.example.dudgns0507.learndoin.Activity;

/**
 * Created by sonbi on 2016-11-07.
 */

public class WordClass {
    String wordContent;
    String meaning;
    long resolveTime;

    public  WordClass() {

    }

    public WordClass(String wordContent, String meaning){
        this.wordContent = wordContent;
        this.meaning = meaning;
    }

    public void add(String wordContent, String meaning) {
        this.wordContent = wordContent;
        this.meaning = meaning;
    }
}
