package com.app.chris.todolist;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.widget.Button;


// TODO: Refactor to use RecyclerView instead of TableLayout(?)

// TODO: Write list items and prioritymark to local storage(Whole list(TableLayout) to local storage)? (ROOM, SQLite)

// TODO: Get list from local storage at startup. (ViewModel(LiveData)), repository) Code Flow Tutorial!

// TODO: MAKE WIDGET!!!

// TODO: Design icon

public class MainActivity extends AppCompatActivity {

    private NoteViewModel noteViewModel;

    private Button roundAddNoteButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_main);

        RecyclerView recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);

        NoteAdapter adapter = new NoteAdapter();
        recyclerView.setAdapter(adapter);

        noteViewModel = ViewModelProviders.of(this).get(NoteViewModel.class);
        noteViewModel.getAllNotes().observe(this, notes -> {
            //Update RecyclerView
            adapter.setNotes(notes);
        });

        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0,
                ItemTouchHelper.LEFT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder viewHolder1) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
                noteViewModel.delete(adapter.getNoteAt(viewHolder.getAdapterPosition()));
            }
        }).attachToRecyclerView(recyclerView);


        roundAddNoteButton = findViewById(R.id.round_addNote_button);
        roundAddNoteButton.setOnClickListener((roundButtonPressed) -> {
            Intent intent = new Intent(MainActivity.this, AddEditActivity.class);
            startActivity(intent);

        });

        // If note is clicked
        adapter.setOnItemClickListener((clickedNote) -> {
            Intent intent = new Intent(MainActivity.this, AddEditActivity.class);
            Note note = adapter.getNoteById(clickedNote.getId());
            // send the note to the other activity for processing.
            intent.putExtra("Note", note);
            startActivity(intent);

        });
    }
}
