package ru.taponapp.intime.fragments

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import ru.taponapp.intime.R
import ru.taponapp.intime.adapters.MainAdapter
import ru.taponapp.intime.databinding.FragmentMainScreenBinding
import ru.taponapp.intime.models.*

class MainScreenFragment: Fragment() {

    interface Callbacks {
        fun onOpenEventDetails(id: Int?)
        fun onOpenNoteDetails(id: Int?)
    }

    private var callbacks: Callbacks? = null

    private var _binding: FragmentMainScreenBinding? = null
    private val binding get() = _binding!!

    private val mainViewModel: MainViewModel by viewModels()

    private var myAdapter: MainAdapter? = null

    private val itemsList: MutableList<Item> = emptyList<Item>().toMutableList()
    private val eventsList: MutableList<Item> = emptyList<Event>().toMutableList()
    private val notesList: MutableList<Item> = emptyList<Note>().toMutableList()

    private var isCreateFabOpened = false

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


    override fun onAttach(context: Context) {
        super.onAttach(context)
        callbacks = context as Callbacks?
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMainScreenBinding.inflate(inflater, container, false)

        myAdapter = MainAdapter(itemsList)

        binding.mainRecyclerView.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            adapter = myAdapter
        }

        isCreateFabOpened = false
        setVisibility()

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mainViewModel.eventsListLiveData.observe(viewLifecycleOwner, { events ->
                eventsList.clear()
                eventsList.addAll(events)
                updateItemsList()
            }
        )

        mainViewModel.notesListLiveData.observe(viewLifecycleOwner, { notes ->
                notesList.clear()
                notesList.addAll(notes)
                updateItemsList()
            }
        )

        binding.createFab.setOnClickListener {
            isCreateFabOpened = !isCreateFabOpened
            setAnimation()
            setVisibility()
        }

        binding.newEventFab.setOnClickListener {
            callbacks?.onOpenEventDetails(null)
        }

        binding.newNoteFab.setOnClickListener {
            callbacks?.onOpenNoteDetails(null)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
        myAdapter = null
    }

    override fun onDetach() {
        super.onDetach()
        callbacks = null
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun updateItemsList() {
        itemsList.clear()
        if (eventsList.isNotEmpty()) {
            itemsList.add(EventsHeader())
            itemsList.addAll(eventsList)
        }
        if (notesList.isNotEmpty()) {
            itemsList.add(NotesHeader())
            itemsList.addAll(notesList)
        }

        binding.mainRecyclerView.adapter?.notifyDataSetChanged()
    }

    private fun setAnimation() {
        if (isCreateFabOpened == true) {
            binding.apply {
                createFab.startAnimation(openAnimation)
                newEventFab.startAnimation(showButtonAnimation)
                newNoteFab.startAnimation(showButtonAnimation)
            }
        } else {
            binding.apply {
                createFab.startAnimation(closeAnimation)
                newEventFab.startAnimation(hideButtonAnimation)
                newNoteFab.startAnimation(hideButtonAnimation)
            }
        }
    }

    private fun setVisibility() {
        if (isCreateFabOpened == true) {
            binding.apply {
                newEventFab.isVisible = true
                newNoteFab.isVisible = true
            }
        } else {
            binding.apply {
                newEventFab.isVisible = false
                newNoteFab.isVisible = false
            }
        }
    }

    companion object {
        fun newInstance(): MainScreenFragment {
            return MainScreenFragment()
        }
    }
}