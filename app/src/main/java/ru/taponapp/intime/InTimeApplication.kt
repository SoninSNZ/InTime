package ru.taponapp.intime

import android.app.Application
import ru.taponapp.intime.repositories.EventsRepository
import ru.taponapp.intime.repositories.NotesRepository

class InTimeApplication: Application() {

    override fun onCreate() {
        super.onCreate()
        EventsRepository.initialize(this)
        NotesRepository.initialize(this)
    }
}