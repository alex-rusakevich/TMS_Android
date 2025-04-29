package com.example.tms_android.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.tms_android.data.model.Note
import com.example.tms_android.data.repository.NotesRepository

class NotesViewModel : ViewModel() {
    private val repository = NotesRepository()
    private val _notes = MutableLiveData<List<Note>>()
    val notes: LiveData<List<Note>> = _notes

    init {
        loadNotes()
    }

    fun addNote(text: String) {
        if (text.isNotBlank()) {
            repository.addNote(text)
            loadNotes()
        }
    }

    fun deleteNote(id: Long) {
        repository.deleteNote(id)
        loadNotes()
    }

    private fun loadNotes() {
        _notes.value = repository.getAllNotes()
    }
}