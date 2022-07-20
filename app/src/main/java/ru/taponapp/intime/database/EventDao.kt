package ru.taponapp.intime.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import ru.taponapp.intime.models.Event
import java.util.*

@Dao
interface EventDao {

    @Query("SELECT * FROM event")
    fun getEvents() : LiveData<List<Event>>

    @Query("SELECT * FROM event WHERE id=(:id)")
    fun getEvent(id: UUID) : LiveData<Event?>

    @Insert
    fun addEvent(event: Event)

    @Query("DELETE FROM event")
    fun deleteEvents()
}