package com.app.chris.todolist;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;

public class TodoWidgetProvider extends AppWidgetProvider {

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {

        // Perform this loop procedure for each App Widget that belongs to this provider
        for (int appWidgetId : appWidgetIds) {
            // Create an Intent to launch Service
            Intent intent = new Intent(context, TodolistRemoteViewsService.class);

            RemoteViews remoteView = new RemoteViews(context.getPackageName(), R.layout.widget_layout);
            remoteView.setRemoteAdapter(R.id.widget_listview, intent);
            remoteView.setEmptyView(R.id.widget_listview, R.id.empty_view);

            // Handle if an item in the listView gets clicked.
            Intent openAppIntent = new Intent(context, MainActivity.class);
            PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, openAppIntent, 0);
            remoteView.setPendingIntentTemplate(R.id.widget_listview, pendingIntent);

            appWidgetManager.updateAppWidget(appWidgetId, remoteView);
        }
        super.onUpdate(context, appWidgetManager, appWidgetIds);
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        super.onReceive(context, intent);

        if (intent.getAction().equals(
                AppWidgetManager.ACTION_APPWIDGET_UPDATE)) {

            AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);

            ComponentName thisAppWidget = new ComponentName(context.getPackageName(), TodoWidgetProvider.class.getName());
            int[] appWidgetIds = appWidgetManager.getAppWidgetIds(thisAppWidget);
            appWidgetManager.notifyAppWidgetViewDataChanged(appWidgetIds,  R.id.widget_listview);

        }
    }
}
