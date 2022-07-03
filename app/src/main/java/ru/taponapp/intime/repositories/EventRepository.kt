package ru.taponapp.intime.repositories

import android.content.Context
import androidx.room.Room
import ru.taponapp.intime.database.EventDatabase
import ru.taponapp.intime.models.Event
import java.lang.IllegalStateException
import java.util.*

private const val DATABASE_NAME = "crime-database"

class EventRepository private constructor(context: Context) {

    private val database: EventDatabase = Room.databaseBuilder(
        context.applicationContext,
        EventDatabase::class.java,
        DATABASE_NAME
    ).build()

    private val eventDao = database.eventDao()

    fun getEvents(): List<Event> = eventDao.getEvents()

    fun getEvent(id: UUID): Event? = eventDao.getEvent(id)

    companion object {
        private var INSTANCE: EventRepository? = null

        fun initialize(context: Context) {
            if (INSTANCE == null) {
                INSTANCE = EventRepository(context)
            }
        }

        fun get(): EventRepository {
            return INSTANCE ?:
            throw IllegalStateException("EventRepository is not initialized")
        }
    }
}