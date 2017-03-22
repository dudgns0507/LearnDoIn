package com.example.dudgns0507.learndoin.Activity.model;

import java.util.ArrayList;

/**
 * Created by pyh42 on 2017-03-22.
 */

public class UserData {
    private Study_time study_time;

    private String id;

    private String create_date;

    private ArrayList<Word> wordLists;

    private String email;

    private String name;

    private boolean type;

    public Study_time getStudy_time ()
    {
        return study_time;
    }

    public void setStudy_time (Study_time study_time)
    {
        this.study_time = study_time;
    }

    public String getId ()
    {
        return id;
    }

    public void setId (String id)
    {
        this.id = id;
    }

    public String getCreate_date ()
    {
        return create_date;
    }

    public void setCreate_date (String create_date)
    {
        this.create_date = create_date;
    }

    public ArrayList<Word> getWordLists ()
    {
        return wordLists;
    }

    public void setWordLists (ArrayList<Word> wordLists)
    {
        this.wordLists = wordLists;
    }

    public void addWordLists (Word word) {
        this.wordLists.add(word);
    }

    public void removeWordLists(int i) {
        this.wordLists.remove(i);
    }

    public String getEmail ()
    {
        return email;
    }

    public void setEmail (String email)
    {
        this.email = email;
    }

    public String getName ()
    {
        return name;
    }

    public void setName (String name)
    {
        this.name = name;
    }

    public boolean getType ()
    {
        return type;
    }

    public void setType (boolean type)
    {
        this.type = type;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [study_time = "+study_time+", id = "+id+", create_date = "+create_date+", wordLists = "+wordLists+", email = "+email+", name = "+name+", type = "+type+"]";
    }
}
