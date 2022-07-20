package ru.taponapp.intime.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import ru.taponapp.intime.R

class EventFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val eventFragmentView = inflater.inflate(R.layout.fragment_event, container, false)

        return eventFragmentView
    }

}