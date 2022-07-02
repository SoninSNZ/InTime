package ru.taponapp.intime.repositories

import android.content.Context
import java.lang.IllegalStateException

class EventRepository private constructor(context: Context) {

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