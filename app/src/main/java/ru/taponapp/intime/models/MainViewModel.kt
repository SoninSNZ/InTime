package ru.taponapp.intime.models

import android.util.Log
import androidx.lifecycle.ViewModel
import ru.taponapp.intime.repositories.EventsRepository
import ru.taponapp.intime.repositories.NotesRepository

class MainViewModel : ViewModel() {

    private val eventsRepository = EventsRepository.getInstance()
    private val notesRepository = NotesRepository.getInstance()

    val eventsListLiveData = eventsRepository.getEvents()
    val notesListLiveData = notesRepository.getNotes()

    val eventsList: MutableList<Item> = mutableListOf()
    val notesList: MutableList<Item> = mutableListOf()

    val itemsList: MutableList<Item> = mutableListOf()


    fun updateItemsList() {
        itemsList.clear()
        itemsList.addAll(eventsList)
        itemsList.addAll(notesList)
    }

    override fun onCleared() {
        super.onCleared()
    }
}