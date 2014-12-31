package com.codepath.simpletodo;

import android.app.Application;

import com.activeandroid.ActiveAndroid;
/**
 * Created by user on 12/30/14.
 */
public class ToDoApplication extends Application  {
    @Override
    public void onCreate() {
        super.onCreate();
        ActiveAndroid.initialize(this);
    }
}
