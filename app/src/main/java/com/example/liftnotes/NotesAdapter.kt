package com.example.liftnotes

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

// Adapter for the RecyclerView to display a list of notes (simple strings)
class NotesAdapter(private val notesList: List<String>) : RecyclerView.Adapter<NotesAdapter.NoteViewHolder>() {

    // ViewHolder class to hold the views for each note item in the RecyclerView
    class NoteViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        // This will hold the TextView that shows the note content
        val noteTextView: TextView = view.findViewById(R.id.noteTextView)
    }

    // This method creates a new ViewHolder when needed
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder {
        // Inflate the layout for each item (note) in the RecyclerView
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_note, parent, false)
        return NoteViewHolder(view)
    }

    // This method binds the data (notes) to the ViewHolder (display each note in the TextView)
    override fun onBindViewHolder(holder: NoteViewHolder, position: Int) {
        // Get the note at the given position and set it to the TextView
        holder.noteTextView.text = notesList[position]
    }

    // Returns the total number of notes in the list
    override fun getItemCount(): Int {
        return notesList.size
    }
}
