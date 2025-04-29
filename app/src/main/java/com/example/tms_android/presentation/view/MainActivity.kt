package com.example.tms_android.presentation.view

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.tms_android.R
import com.example.tms_android.databinding.ActivityMainBinding
import com.example.tms_android.presentation.viewmodel.NotesViewModel

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val viewModel: NotesViewModel by viewModels()

    companion object {
        lateinit var instance: AppCompatActivity
            private set
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        instance = this

        enableEdgeToEdge()

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        setupRecyclerView()
        setupObservers()
        setupListeners()

        instance = this
    }

    private fun setupRecyclerView() {
        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        binding.recyclerView.adapter = NotesAdapter { note ->
            viewModel.deleteNote(note.id)
        }
    }

    private fun setupObservers() {
        viewModel.notes.observe(this) { notes ->
            (binding.recyclerView.adapter as NotesAdapter).submitList(notes)
        }
    }

    private fun setupListeners() {
        binding.addButton.setOnClickListener {
            val text = binding.noteEditText.text.toString()
            viewModel.addNote(text)
            binding.noteEditText.text.clear()
        }
    }
}