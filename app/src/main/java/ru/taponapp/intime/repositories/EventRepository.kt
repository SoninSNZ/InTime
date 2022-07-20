package ru.taponapp.intime.repositories

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.room.Room
import ru.taponapp.intime.database.EventDatabase
import ru.taponapp.intime.models.Event
import java.lang.IllegalStateException
import java.util.*
import java.util.concurrent.Executors

private const val DATABASE_NAME = "main_database"

class EventRepository private constructor(context: Context) {

    private val database: EventDatabase = Room.databaseBuilder(
        context.applicationContext,
        EventDatabase::class.java,
        DATABASE_NAME
    ).build()

    private val eventDao = database.eventDao()
    private val executor = Executors.newSingleThreadExecutor()

    fun getEvents(): LiveData<List<Event>> = eventDao.getEvents()

    fun getEvent(id: UUID): LiveData<Event?> = eventDao.getEvent(id)

    fun addEvent(event: Event) {
        executor.execute {
            eventDao.addEvent(event)
        }
    }

    fun deleteEvents() {
        executor.execute {
            eventDao.deleteEvents()
        }
    }

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