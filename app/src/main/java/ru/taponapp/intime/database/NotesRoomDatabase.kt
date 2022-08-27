package ru.taponapp.intime.database

import androidx.room.Database
import androidx.room.RoomDatabase
import ru.taponapp.intime.models.Note

@Database(entities = [Note::class], version = 2)
abstract class NotesRoomDatabase : RoomDatabase() {

    abstract fun noteDao() : NotesDao
}