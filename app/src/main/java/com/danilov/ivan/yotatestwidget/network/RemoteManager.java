package com.danilov.ivan.yotatestwidget.network;


import android.os.AsyncTask;

import com.danilov.ivan.yotatestwidget.model.Feed;
import com.danilov.ivan.yotatestwidget.network.event.IRemoteServiceCallback;
import com.danilov.ivan.yotatestwidget.network.handler.XmlParserHandler;

import java.util.List;

public class RemoteManager {

    public static AsyncTask getFeeds(IRemoteServiceCallback<List<Feed>> callback, String s) {
        return new XmlParserHandler(callback).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, s);
    }
}
