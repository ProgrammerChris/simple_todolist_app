package com.app.chris.todolist;

import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import java.util.List;

public class TodolistRemoteViewsFactory implements RemoteViewsService.RemoteViewsFactory {

    private Context context;

    private NoteDao noteDao;

    private List<Note> listOfNotes;

    RemoteViews remoteViews;

    public TodolistRemoteViewsFactory(Context context, Intent intent) {
        this.context = context;
    }

    @Override
    public void onCreate() {
        NoteDatabase database = NoteDatabase.getInstance(context.getApplicationContext());

        noteDao = database.noteDao();
        remoteViews = new RemoteViews(context.getPackageName(), R.layout.widget_note);
    }

    @Override
    public void onDataSetChanged() {

        // On data changed get list from DB.
        listOfNotes = noteDao.getAllNotesAsList();

    }

    @Override
    public void onDestroy() {

    }

    @Override
    public int getCount() {
        return listOfNotes.size();
    }

    @Override
    public RemoteViews getViewAt(int position) {

        if (listOfNotes.get(position).getPriority().equals("High")) {
            remoteViews.setImageViewResource(R.id.widget_priority, R.drawable.high_pri);
        } else {
            remoteViews.setImageViewResource(R.id.widget_priority, R.drawable.low_pri);
        }

        remoteViews.setTextViewText(R.id.widget_note, listOfNotes.get(position).getNoteText());

        // Open app when note is clicked
        Intent openAppIntent = new Intent();
        remoteViews.setOnClickFillInIntent(R.id.widget_note, openAppIntent);

        return remoteViews;
    }

    @Override
    public RemoteViews getLoadingView() {
        return null;
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }
}
