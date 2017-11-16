package com.matylionak.valery.domain;

import android.app.Activity;

import com.matylionak.valery.data.Entity.Task;

import java.lang.ref.WeakReference;


public class Package {
    private WeakReference<Activity> activity;
    private int id;
    private Task task;

    public Package(Activity activity, int id, Task task) {
        this.activity = new WeakReference<>(activity);
        this.id = id;
        this.task = task;
    }

    public Package(Activity activity, Task task) {
        this.activity = new WeakReference<>(activity);
        this.task = task;
    }

    public Package(Activity activity, int id) {
        this.activity = new WeakReference<>(activity);
        this.id = id;
    }

    public WeakReference<Activity> getActivity() {
        return activity;
    }



    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Task getTask() {
        return task;
    }
}
