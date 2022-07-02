package ru.taponapp.intime.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import ru.taponapp.intime.R
import ru.taponapp.intime.fragments.MainFragment

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val currentFragment = supportFragmentManager.findFragmentById(R.id.fragmentContainer)
        if (currentFragment == null) {
            val fragment = MainFragment()
            supportFragmentManager
                .beginTransaction()
                .add(R.id.fragmentContainer, fragment)
                .commit()
        }
    }
}


