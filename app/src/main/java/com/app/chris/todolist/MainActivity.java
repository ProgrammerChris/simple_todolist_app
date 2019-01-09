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
import android.widget.ListView;


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

        // Finish activity if there is one already running, and resume the running activity. Like when app is open and widget is clicked to open the app.
        if ((getIntent().getFlags() & Intent.FLAG_ACTIVITY_BROUGHT_TO_FRONT) != 0) {
            finish();
            return;
        }

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
