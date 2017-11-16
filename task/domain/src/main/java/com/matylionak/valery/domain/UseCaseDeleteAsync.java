package com.matylionak.valery.domain;


import android.os.AsyncTask;

import com.matylionak.valery.data.database.DataBaseManager;

public class UseCaseDeleteAsync extends AsyncTask<Package, Void, Void> {
    @Override
    protected Void doInBackground(Package... packages) {
        DataBaseManager manager = new DataBaseManager(packages[0].getActivity().get());
        manager.open(false);
        manager.deleteTask(packages[0].getId());
        manager.close();
        return null;
    }
}
