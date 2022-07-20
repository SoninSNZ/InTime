package ru.taponapp.intime.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import ru.taponapp.intime.R
import ru.taponapp.intime.fragments.EventFragment
import ru.taponapp.intime.fragments.MainFragment
import java.util.*

class MainActivity : AppCompatActivity(), MainFragment.Callbacks {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val currentFragment = supportFragmentManager.findFragmentById(R.id.fragment_container)
        if (currentFragment == null) {
            val mainFragment = MainFragment.newInstance()
            supportFragmentManager
                .beginTransaction()
                .add(R.id.fragment_container, mainFragment)
                .commit()
        }
    }

    override fun onEventSelected(eventID: UUID?) {
        val eventFragment = EventFragment()
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.fragment_container, eventFragment)
            .addToBackStack(null)
            .commit()
    }
}


