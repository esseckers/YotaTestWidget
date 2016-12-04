package com.danilov.ivan.yotatestwidget.network.handler;

import android.os.AsyncTask;
import android.util.Log;

import com.danilov.ivan.yotatestwidget.model.Feed;
import com.danilov.ivan.yotatestwidget.network.XmlService;
import com.danilov.ivan.yotatestwidget.network.event.IRemoteServiceCallback;

import java.util.List;


public class XmlParserHandler extends AsyncTask<String, Void, List<Feed>> {

    private static final String TAG = XmlParserHandler.class.getSimpleName();

    private IRemoteServiceCallback<List<Feed>> callback;

    public XmlParserHandler(IRemoteServiceCallback<List<Feed>> callback) {
        this.callback = callback;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        if (callback != null) {
            callback.onStartTask();
        }
    }

    @Override
    protected List<Feed> doInBackground(String... strings) {
        try {
            return XmlService.getAPI(strings[0]).getFeeds().getChannel().getFeeds();
        } catch (Exception e) {
            Log.e(TAG, e.getMessage() != null ? e.getMessage() : "null");
        }
        return null;
    }

    @Override
    protected void onPostExecute(List<Feed> feeds) {
        super.onPostExecute(feeds);
        if (callback != null) {
            callback.onFinishTask();
            if (feeds != null) {
                callback.onSuccess(feeds);
            }else {
                if (callback != null) {
                    callback.onServerError("No, no, no!!! Firstly check internet connection!!!");
                }
            }
        }
    }
}
