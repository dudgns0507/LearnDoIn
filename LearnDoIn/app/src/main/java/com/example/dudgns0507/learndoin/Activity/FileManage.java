package com.example.dudgns0507.learndoin.Activity;

import com.example.dudgns0507.learndoin.Activity.model.Word;

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

/**
 * Created by sonbi on 2016-10-24.
 */
public class FileManage {

    public static FileManage instace;

    public FileManage()
    {
    }

    public static List<Word> parse(String data, String replace)
    {
        data = data.replace("\n", "\t");
        List<Word> wordList = new ArrayList<>(); // word, definition, time
        StringTokenizer tokenizer = new StringTokenizer(data,replace);
        for(int i=0;i<=tokenizer.countTokens();++i){
            Word temp = new Word();
            temp.setWordContent(tokenizer.nextToken());
            temp.setMeaning(tokenizer.nextToken());
            wordList.add(temp);
        }
        return wordList;
    }
}

