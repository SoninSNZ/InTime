package ru.taponapp.intime.fragments

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import ru.taponapp.intime.R
import ru.taponapp.intime.adapters.MainAdapter
import ru.taponapp.intime.models.Item
import ru.taponapp.intime.models.MainViewModel
import java.util.*

class MainFragment: Fragment() {

    interface Callbacks {
        fun onEventSelected(eventID: UUID?)
    }

    private var callbacks: Callbacks? = null
    lateinit var mainRecyclerView: RecyclerView
    private var mAdapter: MainAdapter = MainAdapter(emptyList<Item>().toMutableList())
    private val mainViewModel: MainViewModel by lazy {
        ViewModelProvider(this).get(MainViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val mainFragmentView = inflater.inflate(R.layout.fragment_main, container, false)

        mainRecyclerView = mainFragmentView.findViewById(R.id.main_recycler_view)
        mainRecyclerView.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        mainRecyclerView.adapter = mAdapter

        val button: ImageButton = mainFragmentView.findViewById(R.id.add_event_button)
        button.setOnClickListener {
//            mainViewModel.addEvent(Event(title = "HELLO", time = "17:46"))
            callbacks?.onEventSelected(null)

        }

        val buttonDel: ImageButton = mainFragmentView.findViewById(R.id.delete_event_button)
        buttonDel.setOnClickListener {
            mainViewModel.deleteEvents()
        }

        return mainFragmentView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mainViewModel.eventsListLiveData.observe(
            viewLifecycleOwner,
            Observer { items ->
                updateUI(items)
            }
        )
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        callbacks = context as Callbacks?
    }

    override fun onDetach() {
        super.onDetach()
        callbacks = null
    }

    fun updateUI(items: List<Item>) {
        mAdapter = MainAdapter(itemsList = items.toMutableList())
        mainRecyclerView.adapter = mAdapter
    }

    companion object {
        fun newInstance(): MainFragment {
            return MainFragment()
        }
    }

}