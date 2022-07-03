package ru.taponapp.intime

import android.app.Application
import ru.taponapp.intime.repositories.EventRepository

class InTimeApplication: Application() {

    override fun onCreate() {
        super.onCreate()
        EventRepository.initialize(this)
    }
}