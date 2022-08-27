package ru.taponapp.intime.repositories

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.room.Room
import ru.taponapp.intime.database.NotesRoomDatabase
import ru.taponapp.intime.models.Note
import java.lang.IllegalStateException
import java.util.concurrent.Executors

private const val DATABASE_NAME = "notes_database"

class NotesRepository private constructor(context: Context) {

    private val notesDatabase = Room.databaseBuilder(
        context,
        NotesRoomDatabase::class.java,
        DATABASE_NAME
    ).fallbackToDestructiveMigration()
        .build()

    private val notesDao = notesDatabase.noteDao()
    private val executor = Executors.newSingleThreadExecutor()

    fun getNotes(): LiveData<List<Note>> = notesDao.getNotes()

    fun getNote(noteID: Int): LiveData<Note?> = notesDao.getNote(noteID)

    fun addNote(note: Note) {
        executor.execute {
            notesDao.addNote(note)
        }
    }

    fun updateNote(note: Note) {
        executor.execute {
            notesDao.updateNote(note)
        }
    }

    fun deleteNote(note: Note) {
        executor.execute {
            notesDao.deleteNote(note)
        }
    }

    fun deleteNotes() {
        executor.execute {
            notesDao.deleteNotes()
        }
    }

    companion object {
        private var INSTANCE: NotesRepository? = null

        fun initialize(context: Context) {
            if (INSTANCE == null) {
                INSTANCE = NotesRepository(context)
            }
        }

        fun getInstance() : NotesRepository {
            return INSTANCE ?:
            throw IllegalStateException("TodoRepository is not initialized")
        }
    }
}