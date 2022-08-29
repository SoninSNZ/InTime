package ru.taponapp.intime.fragments

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.ImageButton
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
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

    private var callbacks: Callbacks? = null

    private val itemsList: MutableList<Item> = emptyList<Item>().toMutableList()
    private val eventsList: MutableList<Item> = emptyList<Item>().toMutableList()
    private val notesList: MutableList<Item> = emptyList<Item>().toMutableList()

    private val mainViewModel: MainViewModel by lazy {
        ViewModelProvider(this).get(MainViewModel::class.java)
    }

    private lateinit var mainScreenRecyclerView: RecyclerView
    private lateinit var mAdapter: MainAdapter

    private lateinit var createFab: FloatingActionButton
    private lateinit var newEventFab: FloatingActionButton
    private lateinit var newNoteFab: FloatingActionButton

    private val openAnimation: Animation by lazy {
        AnimationUtils.loadAnimation(context, R.anim.rotate_open_anim)
    }

    private val closeAnimation: Animation by lazy {
        AnimationUtils.loadAnimation(context, R.anim.rotate_close_anim)
    }

    private val showButtonAnimation: Animation by lazy {
        AnimationUtils.loadAnimation(context, R.anim.show_btns_anim)
    }

    private val hideButtonAnimation: Animation by lazy {
        AnimationUtils.loadAnimation(context, R.anim.hide_btns_anim)
    }

    private var isCreateFabOpened = false

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

        createFab = mainFragmentView.findViewById(R.id.create_btn)
        newEventFab = mainFragmentView.findViewById(R.id.new_event_btn)
        newNoteFab = mainFragmentView.findViewById(R.id.new_note_btn)

        mainScreenRecyclerView = mainFragmentView.findViewById(R.id.main_recycler_view)
        mainScreenRecyclerView.layoutManager = layoutManager
        mainScreenRecyclerView.adapter = mAdapter

        isCreateFabOpened = false
        newEventFab.visibility = View.GONE
        newNoteFab.visibility = View.GONE

        return mainFragmentView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mainViewModel.eventsListLiveData.observe(
            viewLifecycleOwner,
            Observer { events ->
                eventsList.clear()
                eventsList.addAll(events)
                updateList(createItemsList())
            }
        )

        mainViewModel.notesListLiveData.observe(
            viewLifecycleOwner,
            Observer { notes ->
                notes.let {
                    notesList.clear()
                    notesList.addAll(notes)
                    updateList(createItemsList())
                }
            }
        )

        createFab.setOnClickListener {
            setAnimation()
            setVisibility()
            isCreateFabOpened = !isCreateFabOpened
        }

        newEventFab.setOnClickListener {
            callbacks?.onOpenEventDetails(null)
        }

        newNoteFab.setOnClickListener {
            callbacks?.onOpenNoteDetails(null)
        }
    }

    override fun onDetach() {
        super.onDetach()
        callbacks = null
    }

    private fun createItemsList(): MutableList<Item> {
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

    private fun updateList(items: MutableList<Item>) {
        mAdapter = MainAdapter(itemsList = items)
        mainScreenRecyclerView.adapter = mAdapter
    }

    private fun setAnimation() {
        if (isCreateFabOpened == false) {
            createFab.startAnimation(openAnimation)
            newEventFab.startAnimation(showButtonAnimation)
            newNoteFab.startAnimation(showButtonAnimation)
        } else {
            createFab.startAnimation(closeAnimation)
            newEventFab.startAnimation(hideButtonAnimation)
            newNoteFab.startAnimation(hideButtonAnimation)
        }
    }

    private fun setVisibility() {
        if (isCreateFabOpened == false) {
            newEventFab.visibility = View.VISIBLE
            newNoteFab.visibility = View.VISIBLE

        } else {
            newEventFab.visibility = View.GONE
            newNoteFab.visibility = View.GONE
        }
    }

    companion object {
        fun newInstance(): MainScreenFragment {
            return MainScreenFragment()
        }
    }

}