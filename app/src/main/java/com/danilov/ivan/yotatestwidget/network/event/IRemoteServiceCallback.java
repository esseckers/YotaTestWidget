package com.danilov.ivan.yotatestwidget.network.event;



public interface IRemoteServiceCallback<E> {
    /**
     * Start remote call process
     */
    void onStartTask();

    void onSuccess(E response);


    void onServerError(String error);

    /**
     * End remote call process, call in any case after onStart,onSuccess,onFailure,onServerError
     */
    void onFinishTask();
}
