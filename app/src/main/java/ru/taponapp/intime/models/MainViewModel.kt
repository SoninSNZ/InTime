package ru.taponapp.intime.models

import androidx.lifecycle.ViewModel
import ru.taponapp.intime.repositories.EventRepository

class MainViewModel : ViewModel() {

    //TODO: getter for itemsList
    var itemsList = mutableListOf<Item>()
    private val eventsList = mutableListOf<Event>()
    private val todoList = mutableListOf<Todo>()

    private val eventRepository = EventRepository.get()
    val eventsListLiveData = eventRepository.getEvents()

    fun addEvent(event: Event) {
        eventRepository.addEvent(event)
    }

    fun deleteEvents() {
        eventRepository.deleteEvents()
    }

//    init {
//        eventsList.apply {
//            add(Event(title = "HELLO"))
//            add(Event(title = "ITS"))
//            add(Event(title = "ME"))
//        }
//
//        todoList.apply{
//            add(Todo(todoTitle = "I"))
//            add(Todo(todoTitle = "WAS"))
//            add(Todo(todoTitle = "WONDERING"))
//            add(Todo(todoTitle = "IF"))
//        }
//
//        itemsList.apply {
//            add(EventHeader())
//            addAll(eventsList)
//            add(TodoHeader())
//            addAll(todoList)
//        }
//    }

//    fun getItemslist(): MutableList<Item> {
//        return itemsList
//    }

    override fun onCleared() {
        super.onCleared()
    }
}