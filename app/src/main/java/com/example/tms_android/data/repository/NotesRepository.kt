package com.example.tms_android.data.repository

import android.content.Context
import com.example.tms_android.data.model.Note
import com.example.tms_android.presentation.view.MainActivity
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.io.IOException

class NotesRepository {
    private val notes = mutableListOf<Note>()
    private val gson = Gson()
    private val fileName = "notes.json"

    init {
        loadNotes()
    }

    fun getAllNotes(): List<Note> = notes.toList()

    fun addNote(text: String) {
        notes.add(Note(text = text))
        saveNotes()
    }

    fun deleteNote(id: Long) {
        notes.removeAll { it.id == id }
        saveNotes()
    }

    private fun saveNotes() {
        val json = gson.toJson(notes)
        MainActivity.instance.openFileOutput(fileName, Context.MODE_PRIVATE).use {
            it.write(json.toByteArray())
        }
    }

    private fun loadNotes() {
        try {
            MainActivity.instance.openFileInput(fileName).use { inputStream ->
                val json = inputStream.bufferedReader().use { it.readText() }
                val noteType = object : TypeToken<List<Note>>() {}.type
                val loadedNotes: List<Note> = gson.fromJson(json, noteType)
                notes.addAll(loadedNotes)
            }
        } catch (_: IOException) { }
    }
}