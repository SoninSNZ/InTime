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
import ru.taponapp.intime.models.EventsHeader
import ru.taponapp.intime.models.Item
import ru.taponapp.intime.models.MainViewModel
import ru.taponapp.intime.models.NotesHeader

class MainScreenFragment: Fragment() {

    interface Callbacks {
        fun onOpenEventDetails(id: Int?)
        fun onOpenNoteDetails(id: Int?)
    }

    private val mainViewModel: MainViewModel by lazy {
        ViewModelProvider(this).get(MainViewModel::class.java)
    }

    private val itemsList: MutableList<Item> = emptyList<Item>().toMutableList()
    private val eventsList: MutableList<Item> = emptyList<Item>().toMutableList()
    private val notesList: MutableList<Item> = emptyList<Item>().toMutableList()

    private var callbacks: Callbacks? = null

    lateinit var mainScreenRecyclerView: RecyclerView
    private lateinit var mAdapter: MainAdapter


    override fun onAttach(context: Context) {
        super.onAttach(context)
        callbacks = context as Callbacks?
        mAdapter = MainAdapter(emptyList<Item>().toMutableList())
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val mainFragmentView = inflater.inflate(R.layout.fragment_main_screen, container, false)
        val layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)

        mainScreenRecyclerView = mainFragmentView.findViewById(R.id.main_recycler_view)
        mainScreenRecyclerView.layoutManager = layoutManager
        mainScreenRecyclerView.adapter = mAdapter

        val button_event: ImageButton = mainFragmentView.findViewById(R.id.add_event_button)
        button_event.setOnClickListener {
            callbacks?.onOpenEventDetails(null)
        }

        val button_note: ImageButton = mainFragmentView.findViewById(R.id.add_note_button)
        button_note.setOnClickListener {
            callbacks?.onOpenNoteDetails(null)
        }

        val buttonDel: ImageButton = mainFragmentView.findViewById(R.id.delete_event_button)
        buttonDel.setOnClickListener {
            mainViewModel.deleteEvents()
            mainViewModel.deleteNotes()
        }

        return mainFragmentView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mainViewModel.eventsListLiveData.observe(
            viewLifecycleOwner,
            Observer { events ->
                eventsList.clear()
                eventsList.addAll(events)
                updateUI(createItemsList())
            }
        )

        mainViewModel.notesListLiveData.observe(
            viewLifecycleOwner,
            Observer { notes ->
                notes.let {
                    notesList.clear()
                    notesList.addAll(notes)
                    updateUI(createItemsList())
                }
            }
        )
    }

    override fun onDetach() {
        super.onDetach()
        callbacks = null
    }

    fun createItemsList(): MutableList<Item> {
        itemsList.clear()
        if (!eventsList.isEmpty()) {
            itemsList.add(EventsHeader())
            itemsList.addAll(eventsList)
        }
        if (!notesList.isEmpty()) {
            itemsList.add(NotesHeader())
            itemsList.addAll(notesList)
        }
        return itemsList
    }

    fun updateUI(items: MutableList<Item>) {
        mAdapter = MainAdapter(itemsList = items)
        mainScreenRecyclerView.adapter = mAdapter
    }

    companion object {
        fun newInstance(): MainScreenFragment {
            return MainScreenFragment()
        }
    }

}