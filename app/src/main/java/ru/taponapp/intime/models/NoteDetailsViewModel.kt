package ru.taponapp.intime.models

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import ru.taponapp.intime.repositories.NotesRepository

class NoteDetailsViewModel : ViewModel() {

    private val notesRepository = NotesRepository.getInstance()
    private var noteIdLiveData = MutableLiveData<Int>()

    var noteLiveData : LiveData<Note> = Transformations.switchMap(noteIdLiveData) { noteId ->
        notesRepository.getNote(noteId)
    }

    fun loadNote(noteId: Int?) {
        noteIdLiveData.value = noteId
    }

    fun addNote(note: Note) {
        notesRepository.addNote(note)
    }

    fun updateNote(note: Note) {
        notesRepository.updateNote(note)
    }

    fun deleteNote(note: Note) {
        notesRepository.deleteNote(note)
    }
}