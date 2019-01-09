package com.app.chris.todolist;

import android.content.Intent;
import android.widget.RemoteViewsService;

public class TodolistRemoteViewsService extends RemoteViewsService {

    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        return new TodolistRemoteViewsFactory(this.getApplicationContext(), intent);
    }
}
