package com.app.chris.todolist;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

import java.io.Serializable; // To make the not able to be saved to local storage.

@Entity(tableName = "note_table")
public class Note implements Serializable {

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
