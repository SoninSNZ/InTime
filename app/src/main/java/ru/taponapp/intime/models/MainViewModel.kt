package ru.taponapp.intime.models

import androidx.lifecycle.ViewModel
import ru.taponapp.intime.repositories.EventsRepository
import ru.taponapp.intime.repositories.NotesRepository

class MainViewModel : ViewModel() {

    private val eventsRepository = EventsRepository.getInstance()
    private val notesRepository = NotesRepository.getInstance()

    val eventsListLiveData = eventsRepository.getEvents()
    val notesListLiveData = notesRepository.getNotes()

    fun deleteEvents() {
        eventsRepository.deleteEvents()
    }

    fun deleteNotes() {
        notesRepository.deleteNotes()
    }

    override fun onCleared() {
        super.onCleared()
    }
}