package com.danilov.ivan.yotatestwidget.widget;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;

import com.danilov.ivan.yotatestwidget.R;
import com.danilov.ivan.yotatestwidget.Utils;
import com.danilov.ivan.yotatestwidget.view.DialogActivity;


public class YotaWidgetProvider extends AppWidgetProvider {

    @Override
    public void onReceive(Context context, Intent intent) {
        super.onReceive(context, intent);
        startUpdate(context);
        if (intent.getAction().equals(WidgetAction.ACTION_SUBSCRIBE.getActionKey())) {
            startDialogActivity(context);
        } else if (intent.getAction().equals(WidgetAction.ACTION_BACK.getActionKey())) {
            sendEventToService(context, Utils.EXTRA_BACK);
        } else if (intent.getAction().equals(WidgetAction.ACTION_NEXT.getActionKey())) {
            sendEventToService(context, Utils.EXTRA_NEXT);
        }
    }

    private void sendEventToService(Context context, String event) {
        Intent serviceIntent = new Intent(context, UpdateWidgetService.class);
        serviceIntent.setAction(event);
        context.startService(serviceIntent);
    }

    private void startDialogActivity(Context context) {
        Intent intent = new Intent(context, DialogActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        super.onUpdate(context, appWidgetManager, appWidgetIds);
        initClickListeners(context, appWidgetManager, appWidgetIds);
    }

    private void startUpdate(Context context) {
        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);
        ComponentName thisAppWidget = new ComponentName(context.getPackageName(),
                YotaWidgetProvider.class.getName());
        int[] appWidgetIds = appWidgetManager.getAppWidgetIds(thisAppWidget);
        this.onUpdate(context, appWidgetManager, appWidgetIds);
    }

    private void initClickListeners(Context context, AppWidgetManager appWidgetManager,
                                    int[] allWidgetIds) {
        for (int widgetId : allWidgetIds) {
            RemoteViews remoteViews = new RemoteViews(context.getPackageName(),
                    R.layout.yota_widget);
            remoteViews.setOnClickPendingIntent(R.id.widget,
                    getPendingIntent(context, WidgetAction.ACTION_SUBSCRIBE.getActionKey()));
            remoteViews.setOnClickPendingIntent(R.id.indeterminate_progress_native,
                    getPendingIntent(context, WidgetAction.ACTION_SUBSCRIBE.getActionKey()));
            remoteViews.setOnClickPendingIntent(R.id.title_feed,
                    getPendingIntent(context, WidgetAction.ACTION_SUBSCRIBE.getActionKey()));
            remoteViews.setOnClickPendingIntent(R.id.description_feed,
                    getPendingIntent(context, WidgetAction.ACTION_SUBSCRIBE.getActionKey()));
            remoteViews.setOnClickPendingIntent(R.id.next,
                    getPendingIntent(context, WidgetAction.ACTION_NEXT.getActionKey()));
            remoteViews.setOnClickPendingIntent(R.id.back,
                    getPendingIntent(context, WidgetAction.ACTION_BACK.getActionKey()));
            appWidgetManager.updateAppWidget(widgetId, remoteViews);
        }
    }

    private PendingIntent getPendingIntent(Context context, String action) {
        Intent intent = new Intent(context, YotaWidgetProvider.class);
        intent.setAction(action);
        return PendingIntent.getBroadcast(context, 0, intent, 0);
    }
}
