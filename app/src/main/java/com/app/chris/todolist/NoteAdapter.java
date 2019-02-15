package com.app.chris.todolist;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;


public class NoteAdapter extends RecyclerView.Adapter<NoteAdapter.NoteHolder> {
    private List<Note> notes = new ArrayList<>();
    private onItemClickListener listener;

    @NonNull
    @Override
    public NoteHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.activity_note, viewGroup, false);
        return new NoteHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull NoteHolder noteHolder, int i) {
        Note currentNote = notes.get(i);
        if (currentNote.getPriority().equals("High")) {
            noteHolder.priority.setImageResource(R.drawable.high_pri);
        } else {
            noteHolder.priority.setImageResource(R.drawable.low_pri);
        }
        noteHolder.note.setText(currentNote.getNoteText());
    }

    @Override
    public int getItemCount() {
        return notes.size();
    }

    public void setNotes(List<Note> notes) {
        this.notes = notes;
        notifyDataSetChanged();
    }

    public Note getNoteAt(int position) {
        return notes.get(position);
    }

    public Note getNoteById(int id) {
        for (Note note : notes) {
            if (id == note.getId()) {
                return note;
            }
        }
        return null;
    }

    class NoteHolder extends RecyclerView.ViewHolder {
        private ImageView priority;
        private TextView note;

        public NoteHolder(@NonNull View itemView) {
            super(itemView);
            priority = itemView.findViewById(R.id.priority);
            note = itemView.findViewById(R.id.note);

            itemView.setOnClickListener((view) ->{

                    int position = getAdapterPosition();
                    if (listener != null && position != RecyclerView.NO_POSITION) {
                        listener.onItemClick(notes.get(position));
                    }
            });
        }
    }

    public interface onItemClickListener {
        void onItemClick(Note note);
    }

    public void setOnItemClickListener(onItemClickListener listener) {
        this.listener = listener;

    }
}
