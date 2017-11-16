package com.matylionak.valery.domain;


import android.os.AsyncTask;
import android.util.Log;

import com.matylionak.valery.data.Entity.Task;
import com.matylionak.valery.data.database.DataBaseManager;


public class UseCaseGetByIdAsync extends AsyncTask<Package, Void, Task> {


    @Override
    protected Task doInBackground(Package... packages) {
        DataBaseManager manager = new DataBaseManager(packages[0].getActivity().get());
        manager.open(false);
        Task task = manager.getTaskById(packages[0].getId());
        manager.close();
        return task;
    }

}
