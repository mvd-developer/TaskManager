package com.matylionak.valery.domain;

import android.app.Activity;
import android.os.AsyncTask;
import android.util.Log;

import com.matylionak.valery.data.Entity.Task;
import com.matylionak.valery.data.database.DataBaseManager;

import java.util.ArrayList;
import java.util.List;


public class UseCaseGetAllAsync extends AsyncTask<Activity, Void, List<Task>>  {

    @Override
    protected List<Task> doInBackground(Activity... activity) {
        List<Task> tasks = new ArrayList<>();
        DataBaseManager manager = new DataBaseManager(activity[0]);
        manager.open(true);
        tasks.addAll(manager.getTasks());
        manager.close();
        return tasks;
    }

    @Override
    protected void onPostExecute(List<Task> tasks) {
        super.onPostExecute(tasks);
    }
}
