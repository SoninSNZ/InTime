package ru.taponapp.intime.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import ru.taponapp.intime.R
import ru.taponapp.intime.adapters.MainAdapter
import ru.taponapp.intime.models.MainViewModel

class MainFragment: Fragment() {
    private val mAdapter = MainAdapter()

    //TODO: replace ViewModelProviders
    private val mainViewModel: MainViewModel by lazy {
        ViewModelProviders.of(this).get(MainViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val mainFragmentView = inflater.inflate(R.layout.fragment_main, container, false)

        val mainRecyclerView: RecyclerView = mainFragmentView.findViewById(R.id.main_recycler_view)
        mainRecyclerView.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        mainRecyclerView.adapter = mAdapter

        val button: ImageButton = mainFragmentView.findViewById(R.id.mainButton)
        button.setOnClickListener {
            mAdapter.addItem()
        }

        return mainFragmentView
    }

    companion object {
        fun newInstance(): MainFragment {
            return MainFragment()
        }
    }

}