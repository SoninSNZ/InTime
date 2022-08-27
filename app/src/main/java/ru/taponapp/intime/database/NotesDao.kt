package ru.taponapp.intime.database

import androidx.lifecycle.LiveData
import androidx.room.*
import ru.taponapp.intime.models.Note

@Dao
interface NotesDao {

    @Query("SELECT * FROM notes_table")
    fun getNotes() : LiveData<List<Note>>

    @Query("SELECT * FROM notes_table WHERE id=(:id)")
    fun getNote(id: Int) : LiveData<Note?>

    @Insert
    fun addNote(note: Note)

    @Update
    fun updateNote(note: Note)

    @Delete
    fun deleteNote(note: Note)

    @Query("DELETE FROM notes_table")
    fun deleteNotes()
}