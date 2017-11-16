package com.matylionak.valery.task;

import android.databinding.ObservableField;


public class ItemViewModel {
    private ObservableField<String> title = new ObservableField<>("");
    private ObservableField<Integer> progress = new ObservableField<>(1);
    private ObservableField<String> time = new ObservableField<>("19:00");
    private ObservableField<String> date = new ObservableField<>("20 Nov. 2017");
    private ObservableField<String> status = new ObservableField<>(STATUS.NEW_TASK);

    public ObservableField<String> getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title.set(title);
    }

    public ObservableField<Integer> getProgress() {
        return progress;
    }

    public void setProgress(Integer pr) {
        this.progress.set(pr);
    }

    public ObservableField<String> getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time.set(time);
    }

    public ObservableField<String> getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date.set(date);
    }

    public ObservableField<String> getStatus() {
        return status;
    }

    public void setStatus(int i) {
        status.set(STATUS.status[i]);
    }
}
