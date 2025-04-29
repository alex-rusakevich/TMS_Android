package com.example.tms_android.presentation.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.example.tms_android.databinding.FragmentEditNoteBinding
import com.example.tms_android.presentation.viewmodel.NotesViewModel

private const val ARG_NOTE_ID = "noteId"

class EditNoteFragment : Fragment() {
    private lateinit var binding: FragmentEditNoteBinding
    private val viewModel: NotesViewModel by viewModels()
    private var noteId: Long? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            noteId = it.getLong(ARG_NOTE_ID)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentEditNoteBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val note = viewModel.getNote(noteId!!)
        binding.noteEditText.setText(note.text)

        binding.saveButton.setOnClickListener {
            // TODO: Это место действительно обновляет данные, но список не перерисовывается
            viewModel.updateNote(note.id, binding.noteEditText.getText().toString())
            requireActivity().supportFragmentManager.popBackStack()
        }
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param noteId Note's id
         * @return A new instance of fragment EditNoteFragment.
         */
        @JvmStatic
        fun newInstance(noteId: Long) =
            EditNoteFragment().apply {
                arguments = Bundle().apply {
                    putLong(ARG_NOTE_ID, noteId)
                }
            }
    }
}