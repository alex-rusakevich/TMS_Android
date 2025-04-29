package com.example.tms_android.presentation.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.tms_android.databinding.FragmentNoteListBinding
import com.example.tms_android.presentation.viewmodel.NotesViewModel

class NoteListFragment : Fragment() {
    private val viewModel: NotesViewModel by viewModels()
    private lateinit var binding: FragmentNoteListBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        setupRecyclerView()
        setupObservers()
        setupListeners()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentNoteListBinding.inflate(inflater, container, false)
        return binding.root
    }

    private fun setupRecyclerView() {
        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerView.adapter = NotesAdapter(
            onDeleteClick = { note ->
                viewModel.deleteNote(note.id)
            },
            onNoteClick = { note ->
                (MainActivity.instance as MainActivity).editNote(note.id)
            }
        )
    }

    private fun setupObservers() {
        viewModel.notes.observe(viewLifecycleOwner) { notes ->
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

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @return A new instance of fragment NoteListFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance() =
            NoteListFragment()
    }
}