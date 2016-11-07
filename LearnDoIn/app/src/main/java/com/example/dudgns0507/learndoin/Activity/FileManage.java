package com.example.dudgns0507.learndoin.Activity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

/**
 * Created by sonbi on 2016-10-24.
 */
public class FileManage {

    public static FileManage instace;

    public FileManage()
    {
    }

    public List<WordClass> parse(String data, String replace)
    {
        List<WordClass> wordList = new ArrayList<>(); // word, definition, time
        StringTokenizer tokenizer = new StringTokenizer(data,replace);
        for(int i=0;i<=tokenizer.countTokens();++i){
            WordClass temp = new WordClass();
            temp.wordContent = tokenizer.nextToken();
            temp.meaning = tokenizer.nextToken();
            wordList.add(temp);
        }
        return wordList;
    }
}

