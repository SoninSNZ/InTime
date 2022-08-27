package ru.taponapp.intime.database

import androidx.room.Database
import androidx.room.RoomDatabase
import ru.taponapp.intime.models.Event

@Database(entities = [Event::class], version = 6)
abstract class EventsRoomDatabase : RoomDatabase() {

    abstract fun eventDao() : EventsDao
}