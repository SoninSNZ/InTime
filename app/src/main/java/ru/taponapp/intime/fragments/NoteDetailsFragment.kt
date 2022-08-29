package ru.taponapp.intime.fragments

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import ru.taponapp.intime.R
import ru.taponapp.intime.models.Note
import ru.taponapp.intime.models.NoteDetailsViewModel

private const val NOTE_ID = "note_id"

class NoteDetailsFragment: Fragment() {

    private var note: Note = Note()
    private var initialId: Int? = null
    private lateinit var noteTitle: EditText
    private lateinit var noteText: EditText
    private val noteDetailsViewModel: NoteDetailsViewModel by lazy {
        ViewModelProvider(this).get(NoteDetailsViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initialId = arguments?.getSerializable(NOTE_ID) as Int?
        if (initialId != null) {
            noteDetailsViewModel.loadNote(initialId)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val noteDetailsView = inflater.inflate(R.layout.fragment_note_details, container, false)
        noteTitle = noteDetailsView.findViewById(R.id.details_title_text)
        noteText = noteDetailsView.findViewById(R.id.field_note_text)
        return noteDetailsView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (initialId != null) {
            noteDetailsViewModel.noteLiveData.observe(
                viewLifecycleOwner,
                Observer { note ->
                    note.let {
                        this.note = note
                        updateUI()
                    }
                })
        }
    }

    override fun onStart() {
        super.onStart()

        val noteTitleWatcher : TextWatcher = object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                note.title = s.toString()
            }

            override fun afterTextChanged(s: Editable?) {
            }
        }

        val noteTextWatcher : TextWatcher = object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                note.text = s.toString()
            }

            override fun afterTextChanged(s: Editable?) {
            }
        }

        noteTitle.addTextChangedListener(noteTitleWatcher)
        noteText.addTextChangedListener(noteTextWatcher)
    }

    override fun onDestroy() {
        super.onDestroy()
        if ((note.title != "") or (note.text != "")) {
            if (initialId != null) {
                noteDetailsViewModel.updateNote(note)
            } else {
                noteDetailsViewModel.addNote(note)
            }
        }
    }

    fun updateUI() {
        noteTitle.setText(note.title)
        noteText.setText(note.text)
    }

    companion object{
        fun newInstance(noteID: Int?): NoteDetailsFragment {
            val args = Bundle().apply {
                putSerializable(NOTE_ID, noteID)
            }
            return NoteDetailsFragment().apply {
                arguments = args
            }
        }
    }

}