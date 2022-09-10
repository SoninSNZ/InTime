package ru.taponapp.intime.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import ru.taponapp.intime.R
import ru.taponapp.intime.adapters.MainAdapter
import ru.taponapp.intime.databinding.ActivityMainBinding
import ru.taponapp.intime.fragments.*

class MainActivity :
    AppCompatActivity(),
    MainScreenFragment.Callbacks,
    MainAdapter.Callbacks {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        hideBottomNavigation()

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .add(R.id.fragment_container, MainScreenFragment.newInstance())
                .addToBackStack("msf")
                .commit()

            showBottomNavigation()
        }

        supportFragmentManager.addOnBackStackChangedListener() {
            when (supportFragmentManager.findFragmentById(R.id.fragment_container)) {
                is MainScreenFragment -> showBottomNavigation()
                is PlacesScreenFragment -> showBottomNavigation()
                is SettingsScreenFragment -> showBottomNavigation()
                is EventDetailsFragment -> hideBottomNavigation()
                is NoteDetailsFragment -> hideBottomNavigation()
            }
        }
    }

    override fun onStart() {
        super.onStart()

        binding.bottomNavigation.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.main_screen_menu_item -> {
                    replaceFragment(MainScreenFragment.newInstance())
                }
                R.id.places_screen_menu_item -> {
                    replaceFragment(PlacesScreenFragment.newInstance())
                }
                R.id.settings_screen_menu_item -> {
                    replaceFragment(SettingsScreenFragment.newInstance())
                }
            }
            true
        }

        binding.bottomNavigation.setOnItemReselectedListener { }
    }

    override fun onOpenEventDetails(eventId: Int?) {
        val eventDetailsFragment = EventDetailsFragment.newInstance(eventId)
        replaceFragment(eventDetailsFragment)
    }

    override fun onOpenNoteDetails(noteId: Int?) {
        val noteDetailsFragment = NoteDetailsFragment.newInstance(noteId)
        replaceFragment(noteDetailsFragment)
    }

    override fun onEventSelected(id: Int) {
        onOpenEventDetails(id)
    }

    override fun onNoteSelected(id: Int) {
        onOpenNoteDetails(id)
    }

    fun hideBottomNavigation() {
        binding.bottomNavigation.isVisible = false
    }

     fun showBottomNavigation() {
        binding.bottomNavigation.isVisible = true
    }

    private fun replaceFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, fragment)
            .addToBackStack(null)
            .commit()
    }
}


