package com.app.chris.todolist;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.widget.TextView;

import java.io.Serializable;

@Entity(tableName = "note_table")
public class Note implements Serializable { // Serializable to be put as extra with Intent.

    @PrimaryKey(autoGenerate = true)
    private int id;

    private String priority;

    private String noteText;

    public Note(String priority, String noteText) {
        this.priority = priority;
        this.noteText = noteText;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public String getPriority() {
        return priority;
    }

    public String getNoteText() {
        return noteText;
    }
}
