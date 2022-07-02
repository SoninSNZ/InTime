package ru.taponapp.intime.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import ru.taponapp.intime.R
import ru.taponapp.intime.adapters.MainAdapter
import ru.taponapp.intime.data.SavedData
import ru.taponapp.intime.interfaces.Database


class MainFragment: Fragment() {
    private val mAdapter = MainAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val fragmentView = inflater.inflate(R.layout.fragment_main, container, false)

        val recyclerViewMain: RecyclerView = fragmentView.findViewById(R.id.recyclerViewMain)
        recyclerViewMain.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        recyclerViewMain.adapter = mAdapter

        val button: ImageButton = fragmentView.findViewById(R.id.mainButton)
        button.setOnClickListener {
            mAdapter.addItem()
        }

        return fragmentView
    }

}