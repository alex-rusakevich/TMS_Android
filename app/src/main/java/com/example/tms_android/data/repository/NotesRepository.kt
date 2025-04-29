package com.example.tms_android.data.repository

import com.example.tms_android.data.model.Note

class NotesRepository {
    private val notes = mutableListOf<Note>()

    fun getAllNotes(): List<Note> = notes.toList()

    fun addNote(text: String) {
        notes.add(Note(text = text))
    }

    fun deleteNote(id: Long) {
        notes.removeAll { it.id == id }
    }
}