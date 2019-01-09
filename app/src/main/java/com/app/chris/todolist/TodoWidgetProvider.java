package com.app.chris.todolist;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
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

            RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.widget_layout);
            views.setRemoteAdapter(appWidgetId, R.id.widget_listview, intent);
            views.setEmptyView(R.id.widget_listview, R.id.empty_view);

            // Intent to make widget clickable to open the app.
            Intent openAppIntent = new Intent(context, MainActivity.class);
            PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, openAppIntent, 0);
            views.setOnClickPendingIntent(R.id.open_app, pendingIntent);

            // NO "setOnClickFillInIntent()"; ALL OF THE WIDGET TO BE CLICKED AND OPEN MAIN ACTIVITY ON CLICK! ITEMS DO NOT NEED A "onClick"-feature.
            
            appWidgetManager.updateAppWidget(appWidgetId, views);
        }
        super.onUpdate(context, appWidgetManager, appWidgetIds);
    }


}
