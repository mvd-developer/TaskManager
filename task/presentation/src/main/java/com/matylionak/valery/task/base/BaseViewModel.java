package com.matylionak.valery.task.base;

/**
 * Interface to sync activity and view model lifecycle
 */
public interface BaseViewModel {

    void init();

    void release();

    void resume();

    void pause();


}
