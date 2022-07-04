package ru.taponapp.intime.models

import androidx.lifecycle.ViewModel

class MainViewModel : ViewModel() {

    //TODO: getter for itemsList
    val itemsList = mutableListOf<Item>()
    private val eventsList = mutableListOf<Event>()
    private val todoList = mutableListOf<Todo>()

    init {
        eventsList.apply {
            add(Event(title = "HELLO"))
            add(Event(title = "ITS"))
            add(Event(title = "ME"))
        }

        todoList.apply{
            add(Todo(todoTitle = "I"))
            add(Todo(todoTitle = "WAS"))
            add(Todo(todoTitle = "WONDERING"))
            add(Todo(todoTitle = "IF"))
        }

        itemsList.apply {
            add(EventHeader())
            addAll(eventsList)
            add(TodoHeader())
            addAll(todoList)
        }
    }

    fun getItemslist(): MutableList<Item> {
        return itemsList
    }
}