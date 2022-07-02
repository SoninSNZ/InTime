package ru.taponapp.intime.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import ru.taponapp.intime.models.Event

@Database(entities = [ Event::class ], version = 1)
@TypeConverters(EventTypeConverters::class)
abstract class EventDatabase : RoomDatabase() {

    abstract fun eventDao() : EventDao
}