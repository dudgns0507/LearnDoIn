package com.example.dudgns0507.learndoin.Activity;

import com.example.dudgns0507.learndoin.Activity.model.Word;

import java.util.*;

/**
 * Created by sonbi on 2016-11-04.
 */
public class WordPackage {
    List<Word> wordList = new ArrayList<>(); // word, definition, time
    int priority; // Loading Priority
    List<Long> studyDateList = new ArrayList<>();

    public WordPackage(List<Word> wordList, int priority)
    {
        this.wordList = wordList;
        this. priority = priority;
        Date e = new Date();
        studyDateList.add(studyDateList.size()+1,e.getTime());
    }

    public void addWord(Word addedWord)
    {
        this.wordList.add(addedWord);
    }

    public void removeWord(Word removedWord){
        for (Word word: wordList) {
            if(word.equals(removedWord)){
                wordList.remove(word);
            }
        }
    }

}
