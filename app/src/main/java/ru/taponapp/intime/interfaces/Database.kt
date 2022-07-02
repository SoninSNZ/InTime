package ru.taponapp.intime.interfaces

interface Database {
    fun loadEventsList()
    fun saveEventsList()
    fun loadTodoList()
    fun saveTodoList()
}