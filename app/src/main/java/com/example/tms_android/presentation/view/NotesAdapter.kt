package com.example.tms_android.presentation.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.tms_android.R
import com.example.tms_android.data.model.Note
import java.text.SimpleDateFormat
import java.util.Locale

class NotesAdapter(
    private val onDeleteClick: (Note) -> Unit,
    private val onNoteClick: (Note) -> Unit
) : ListAdapter<Note, NotesAdapter.NoteViewHolder>(NoteDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_note, parent, false)
        return NoteViewHolder(view)
    }

    override fun onBindViewHolder(holder: NoteViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class NoteViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val noteText: TextView = view.findViewById(R.id.noteText)
        private val noteDate: TextView = view.findViewById(R.id.noteDate)
        private val deleteButton: ImageButton = view.findViewById(R.id.deleteButton)
        private val listItem: View = view

        fun bind(note: Note) {
            noteText.text = note.text
            noteDate.text = SimpleDateFormat("dd.MM.yyyy HH:mm", Locale.getDefault())
                .format(note.createdAt)

            listItem.setOnClickListener {
                onNoteClick(note)
            }
            deleteButton.setOnClickListener { onDeleteClick(note) }
        }
    }

    class NoteDiffCallback : DiffUtil.ItemCallback<Note>() {
        override fun areItemsTheSame(oldItem: Note, newItem: Note): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Note, newItem: Note): Boolean {
            return oldItem == newItem
        }
    }
}