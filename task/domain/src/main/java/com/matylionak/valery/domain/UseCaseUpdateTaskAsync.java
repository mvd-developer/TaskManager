package com.matylionak.valery.domain;

import android.os.AsyncTask;

import com.matylionak.valery.data.database.DataBaseManager;


public class UseCaseUpdateTaskAsync extends AsyncTask<Package, Void, Void> {
    @Override
    protected Void doInBackground(Package... packages) {
        DataBaseManager manager = new DataBaseManager(packages[0].getActivity().get());
        manager.open(true);
        manager.updateTask(packages[0].getTask());
        manager.close();

        return null;
    }
}
