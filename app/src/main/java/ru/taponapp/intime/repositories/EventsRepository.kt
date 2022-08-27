package ru.taponapp.intime.repositories

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.room.Room
import ru.taponapp.intime.database.EventsRoomDatabase
import ru.taponapp.intime.models.Event
import java.lang.IllegalStateException
import java.util.concurrent.Executors

private const val DATABASE_NAME = "events_database"

class EventsRepository private constructor(context: Context) {

    private val eventsDatabase: EventsRoomDatabase = Room.databaseBuilder(
        context.applicationContext,
        EventsRoomDatabase::class.java,
        DATABASE_NAME
    ).fallbackToDestructiveMigration()
        .build()

    private val eventDao = eventsDatabase.eventDao()
    private val executor = Executors.newSingleThreadExecutor()

    fun getEvents(): LiveData<List<Event>> = eventDao.getEvents()

    fun getEvent(id: Int): LiveData<Event> = eventDao.getEvent(id)

    fun addEvent(event: Event) {
        executor.execute {
            eventDao.addEvent(event)
        }
    }

    fun updateEvent(event: Event) {
        executor.execute {
            eventDao.updateEvent(event)
        }
    }

    fun deleteEvent(event: Event) {
        executor.execute {
            eventDao.deleteEvent(event)
        }
    }

    fun deleteEvents() {
        executor.execute {
            eventDao.deleteEvents()
        }
    }

    companion object {
        private var INSTANCE: EventsRepository? = null

        fun initialize(context: Context) {
            if (INSTANCE == null) {
                INSTANCE = EventsRepository(context)
            }
        }

        fun getInstance(): EventsRepository {
            return INSTANCE ?:
            throw IllegalStateException("EventRepository is not initialized")
        }
    }
}