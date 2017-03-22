package com.example.dudgns0507.learndoin.Activity.model;

import java.util.ArrayList;

/**
 * Created by pyh42 on 2017-03-22.
 */

public class Study_time {
    private ArrayList<String> finish_time;

    private ArrayList<String> start_time;

    public ArrayList<String> getFinish_time() {
        return finish_time;
    }

    public void setFinish_time(ArrayList<String> finish_time) {
        this.finish_time = finish_time;
    }

    public ArrayList<String> getStart_time() {
        return start_time;
    }

    public void setStart_time(ArrayList<String> start_time) {
        this.start_time = start_time;
    }

    public void addStart_time(String time) {
        this.start_time.add(time);
    }

    public void addFinish_time(String time) {
        this.finish_time.add(time);
    }

    @Override
    public String toString()
    {
        return "ClassPojo [finish_time = "+finish_time+", start_time = "+start_time+"]";
    }
}
