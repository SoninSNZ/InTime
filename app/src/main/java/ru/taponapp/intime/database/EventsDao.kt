package ru.taponapp.intime.database

import androidx.lifecycle.LiveData
import androidx.room.*
import ru.taponapp.intime.models.Event

@Dao
interface EventsDao {

    @Query("SELECT * FROM events_table ORDER BY millis_since_epoch ASC")
    fun getEvents() : LiveData<List<Event>>

    @Query("SELECT * FROM events_table WHERE :id like id")
    fun getEvent(id: Int) : LiveData<Event>

    @Insert
    fun addEvent(event: Event)

    @Update
    fun updateEvent(event: Event)

    @Delete
    fun deleteEvent(event: Event)

    @Query("DELETE FROM events_table")
    fun deleteEvents()
}