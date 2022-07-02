package ru.taponapp.intime.data

import ru.taponapp.intime.interfaces.Database
import ru.taponapp.intime.models.*

class SavedData(): Database {
    private var itemsList: MutableList<Item> = mutableListOf()

    init {
        if(itemsList.isEmpty()) {
            fetchItems()
        }
    }

    fun getItemsList(): MutableList<Item> {
        return itemsList
    }

    private fun fetchItems() {
        // It will be replaced with data from DB
        itemsList.apply {
            add(EventHeader())
            add(Event(title = "HELLO"))
            add(Event(title = "ITS"))
            add(Event(title = "ME"))
            add(TodoHeader())
            add(Todo(todoTitle = "I"))
            add(Todo(todoTitle = "WAS"))
            add(Todo(todoTitle = "WONDERING"))
            add(Todo(todoTitle = "IF"))
        }
    }

    override fun loadEventsList() {
        TODO("Not yet implemented")
    }

    override fun saveEventsList() {
        TODO("Not yet implemented")
    }

    override fun loadTodoList() {
        TODO("Not yet implemented")
    }

    override fun saveTodoList() {
        TODO("Not yet implemented")
    }
}