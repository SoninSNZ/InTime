package ru.taponapp.intime.models

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import ru.taponapp.intime.repositories.EventsRepository

class EventDetailsViewModel() : ViewModel() {
    private val eventsRepository = EventsRepository.getInstance()
    private var eventIdLiveData = MutableLiveData<Int>()

    var eventLiveData : LiveData<Event> = Transformations.switchMap(eventIdLiveData) { eventID ->
        eventsRepository.getEvent(eventID)
    }

    fun loadEvent(id: Int?) {
        eventIdLiveData.value = id
    }

    fun addEvent(event: Event) {
        eventsRepository.addEvent(event)
    }

    fun updateEvent(event: Event) {
        eventsRepository.updateEvent(event)
    }

}