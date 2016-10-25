package com.example.dudgns0507.learndoin.Activity;

import java.util.HashMap;
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

    public Map<String,String> parse(String data, String replace)
    {
        Map<String,String> wordData = new HashMap<String,String>();
        StringTokenizer tokenizer = new StringTokenizer(data,replace);
        for(int i=0;i<=tokenizer.countTokens();++i){
            String key = tokenizer.nextToken();
            String value = tokenizer.nextToken();
            wordData.put(key,value);
        }
        return wordData;
    }
}

