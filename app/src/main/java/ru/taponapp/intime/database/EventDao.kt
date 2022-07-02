package ru.taponapp.intime.database

import androidx.room.Dao
import androidx.room.Query
import ru.taponapp.intime.models.Event
import java.util.*

@Dao
interface EventDao {

    @Query("SELECT * FROM event")
    fun getEvents() : List<Event>

    @Query("SELECT * FROM event WHERE id=(:id)")
    fun getEvent(id: UUID) : Event?
}