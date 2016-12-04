package com.danilov.ivan.yotatestwidget.widget;

import android.app.Service;
import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.RemoteViews;
import android.widget.Toast;

import com.danilov.ivan.yotatestwidget.R;
import com.danilov.ivan.yotatestwidget.Utils;
import com.danilov.ivan.yotatestwidget.model.Feed;
import com.danilov.ivan.yotatestwidget.network.RemoteManager;
import com.danilov.ivan.yotatestwidget.network.event.IRemoteServiceCallback;

import java.util.List;
import java.util.Random;
public class UpdateWidgetService extends Service {
    private static final long DELAY = 60 * 1000L;

    private Handler handler;
    private Runnable runnable;
    private String rssUrl;
    private RemoteViews remoteViews;
    private List<Feed> feeds;
    private int position;

    @Override
    public void onCreate() {
        super.onCreate();
        handler = new Handler();
        runnable = new Runnable() {
            public void run() {
                if (rssUrl != null && !rssUrl.isEmpty()) {
                    updateWidget(rssUrl);
                }
            }
        };
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if (intent != null) {
            if (intent.getAction().equals(Utils.EXTRA_KEY)) {
                rssUrl = intent.getStringExtra(Utils.EXTRA_KEY);
                handler.removeCallbacks(runnable);
                runnable.run();
            } else if (intent.getAction().equals(Utils.EXTRA_NEXT)) {
                position++;
                if (feeds != null && feeds.size() > 0) {
                    if (position < feeds.size()) {
                        updateFeed(feeds.get(position));
                    } else {
                        position = feeds.size();
                    }
                }
            } else if (intent.getAction().equals(Utils.EXTRA_BACK)) {
                position--;
                if (feeds != null && feeds.size() > 0) {
                    if (position >= 0) {
                        updateFeed(feeds.get(position));
                    } else {
                        position = 0;
                    }
                }
            }
        }
        return super.onStartCommand(intent, flags, startId);
    }

    private void updateWidget(String rssUrl) {
        remoteViews = new RemoteViews(getPackageName(),
                R.layout.yota_widget);
        RemoteManager.getFeeds(new IRemoteServiceCallback<List<Feed>>() {
            @Override
            public void onStartTask() {
                showProgress(true);
            }

            @Override
            public void onSuccess(List<Feed> response) {
                UpdateWidgetService.this.feeds = response;
                if (feeds != null && feeds.size() > 0) {
                    updateFeed(feeds.get(new Random().nextInt(feeds.size() - 1)));
                }
            }

            @Override
            public void onServerError(String error) {
                Toast.makeText(UpdateWidgetService.this, error, Toast.LENGTH_LONG).show();
                updateFeed(null);
            }

            @Override
            public void onFinishTask() {
                showProgress(false);
                handler.postDelayed(runnable, DELAY);
            }
        }, rssUrl);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    private void updateFeed(Feed data) {
        remoteViews.setTextViewText(R.id.title_feed,
                data == null ? "oops, look down"
                        : data.getTitle());
        remoteViews.setTextViewText(R.id.description_feed,
                data == null ? "oops, look down"
                        : data.getDescription());
        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(this);
        ComponentName thisAppWidget = new ComponentName(getPackageName(),
                YotaWidgetProvider.class.getName());
        int[] appWidgetIds = appWidgetManager.getAppWidgetIds(thisAppWidget);
        for (int widgetId : appWidgetIds) {
            appWidgetManager.updateAppWidget(widgetId, remoteViews);
        }
    }

    private void showProgress(boolean progress) {
        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(this);
        ComponentName thisAppWidget = new ComponentName(getPackageName(),
                YotaWidgetProvider.class.getName());
        int[] appWidgetIds = appWidgetManager.getAppWidgetIds(thisAppWidget);
        for (int widgetId : appWidgetIds) {
            loadingProgress(progress);
            appWidgetManager.updateAppWidget(widgetId, remoteViews);
        }
    }

    private void loadingProgress(boolean load) {
        remoteViews.setViewVisibility(R.id.indeterminate_progress_native, load ? View.VISIBLE : View.GONE);
        remoteViews.setViewVisibility(R.id.widget, load ? View.GONE : View.VISIBLE);
    }
}
