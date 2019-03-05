package com.app.chris.todolist;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;


public class AddEditActivity extends AppCompatActivity {

    private NoteViewModel noteViewModel;

    private TextView title;
    private Boolean highPriChecked = false;
    private Button highPri;
    private Boolean lowPriChecked = true;
    private Button lowPri;
    private EditText noteText;
    private Button cancelButton;
    private Button addNoteButton;

    private Note noteToEdit;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT); // LOCK IN PORTRAIT MODE
        setContentView(R.layout.activity_addoredit);

        title = findViewById(R.id.addOrEditTitle);
        highPri = findViewById(R.id.high_pri_button);
        lowPri = findViewById(R.id.low_pri_button);
        lowPri.setText(R.string.checkmark); // Pre check button
        noteText = findViewById(R.id.add_note_text_box);
        cancelButton = findViewById(R.id.cancel_button);
        addNoteButton = findViewById(R.id.add_note_button);

        // Getting the note sent from main activity to be edited, if there is one.
        Intent extra = getIntent();
        if (extra.getSerializableExtra("Note") != null)   {
            noteToEdit = (Note) extra.getSerializableExtra("Note");
            // Changing the title and the text of the "Add" button.
            title.setText(R.string.editNote);
            addNoteButton.setText(R.string.save);

            // checking of the priority of the clicked note.
            if (noteToEdit.getPriority().equals("High"))    {
                highPri.setText(R.string.checkmark);
                lowPri.setText("");
                highPriChecked = true;
            } else if (noteToEdit.getPriority().equals("Low"))  {
                lowPri.setText(R.string.checkmark);
                lowPriChecked = true;
            }
            //setting the text into the edit text.
            noteText.setText(noteToEdit.getNoteText().trim());
        }

        noteViewModel = ViewModelProviders.of(this).get(NoteViewModel.class);

        // Softkeyboard toggle
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
        noteText.requestFocus();

        highPri.setOnClickListener(view_highPri_selected -> {
            highPriChecked = true;
            lowPriChecked = false;
            highPri.setText(R.string.checkmark);
            lowPri.setText("");
        });

        lowPri.setOnClickListener(view_lowPri_selected -> {
            lowPriChecked = true;
            highPriChecked = false;
            lowPri.setText(R.string.checkmark);
            highPri.setText("");
        });

        cancelButton.setOnClickListener(cancelButtonPressed -> {

            highPriChecked = false;
            lowPriChecked = false;
            finish();
        });

        addNoteButton.setOnClickListener(addNoteButtonPressed -> {
            if (highPriChecked || lowPriChecked) {

                if (!String.valueOf(noteText.getText()).trim().isEmpty()) {

                    // Note(Entity) members
                    String noteTextAsString = Character.toUpperCase(String.valueOf(noteText.getText()).trim().charAt(0)) + String.valueOf(noteText.getText()).trim().substring(1);
                    String priority = "";

                    if (highPriChecked) {
                        priority = "High";
                        highPriChecked = false;
                    } else if (lowPriChecked) {
                        priority = "Low";
                        lowPriChecked = false;
                    }

                    // If note clicked, let it be edited, else add new note
                    if (extra.getSerializableExtra("Note") != null)   {
                        Note updatedNote = new Note(priority, noteTextAsString);
                        updatedNote.setId(noteToEdit.getId());
                        // Update note in DB.
                        noteViewModel.update(updatedNote);
                    } else {
                        // Adds note to DB.
                        noteViewModel.insert(new Note(priority, noteTextAsString));
                    }

                    finish(); // End the activity, back to list of notes.
                }
            }
        });
    }
}
