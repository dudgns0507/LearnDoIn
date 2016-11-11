package com.example.dudgns0507.learndoin.Activity;

import android.app.Application;

/**
 * Created by pyh42 on 2016-11-10.
 */

public class UserInfo extends Application {

    private String id, name;

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
