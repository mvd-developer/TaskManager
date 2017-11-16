package com.matylionak.valery.task;


import android.app.Activity;
import android.content.Intent;
import android.databinding.ObservableField;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;

import com.matylionak.valery.data.Entity.Task;
import com.matylionak.valery.domain.UseCaseGetAllAsync;
import com.matylionak.valery.task.base.BaseViewModel;
import com.matylionak.valery.task.databinding.ActivityMainBinding;

import java.util.List;

public class MainActivityViewModel implements BaseViewModel {
    public enum STATE {PROGRESS, DATA}

    private Activity activity;
    private ActivityMainBinding binding;
    private List<Task> tasks;
    private MyAdaptor adaptor;
    private ObservableField<STATE> state = new ObservableField<>(STATE.PROGRESS);
    private UseCaseGetAllAsync allAsync;

    @Override
    public void init() {
        binding.recycle.setLayoutManager(new LinearLayoutManager(activity));
        adaptor = new MyAdaptor();
        adaptor.setActivity(activity);
        binding.recycle.setAdapter(adaptor);
    }

    @Override
    public void release() {

    }

    @Override
    public void resume() {
        state.set(STATE.PROGRESS);
        try {
            allAsync = new UseCaseGetAllAsync();
            tasks = allAsync.execute(activity).get();
            if (tasks.size()!=0){
                state.set(STATE.DATA);
            }

        } catch (Exception w) {
            Log.e("TAG", "interapt.exeption" + w.getMessage());
        }

        adaptor.setList(tasks);


    }

    @Override
    public void pause() {
        if (!allAsync.isCancelled()) {
            allAsync.cancel(true);
        }
    }


    public void startNewTask() {
        Intent intent = new Intent(activity, DetailsActivity.class);
        intent.putExtra(CONSTANTS.EMPTY, CONSTANTS.EMPTY);
        activity.startActivity(intent);


    }


    public void setBinding(ActivityMainBinding binding) {
        this.binding = binding;
    }

    public void setActivity(Activity activity) {
        this.activity = activity;
    }


    public ObservableField<STATE> getState() {
        return state;
    }

    public void setState(ObservableField<STATE> state) {
        this.state = state;
    }
}
