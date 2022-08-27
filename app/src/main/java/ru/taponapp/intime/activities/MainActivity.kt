package ru.taponapp.intime.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import ru.taponapp.intime.R
import ru.taponapp.intime.adapters.MainAdapter
import ru.taponapp.intime.fragments.EventDetailsFragment
import ru.taponapp.intime.fragments.MainScreenFragment
import ru.taponapp.intime.fragments.NoteDetailsFragment

class MainActivity :
    AppCompatActivity(),
    MainScreenFragment.Callbacks,
    MainAdapter.Callbacks {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val currentFragment = supportFragmentManager.findFragmentById(R.id.fragment_container)

        if (currentFragment == null) {
            val mainScreenFragment = MainScreenFragment.newInstance()
            supportFragmentManager
                .beginTransaction()
                .add(R.id.fragment_container, mainScreenFragment)
                .commit()
        }
    }

    override fun onOpenEventDetails(eventId: Int?) {
        val eventDetailsFragment = EventDetailsFragment.newInstance(eventId)
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.fragment_container, eventDetailsFragment)
            .addToBackStack(null)
            .commit()
    }

    override fun onOpenNoteDetails(noteId: Int?) {
        val noteDetailsFragment = NoteDetailsFragment.newInstance(noteId)
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.fragment_container, noteDetailsFragment)
            .addToBackStack(null)
            .commit()
    }

    override fun onMainScreenEventSelected(id: Int) {
        onOpenEventDetails(id)
    }

    override fun onMainScreenNoteSelected(id: Int) {
        onOpenNoteDetails(id)
    }
}


