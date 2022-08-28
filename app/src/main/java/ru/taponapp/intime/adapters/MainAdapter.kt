package ru.taponapp.intime.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ru.taponapp.intime.R
import ru.taponapp.intime.models.*
import kotlin.Exception

class MainAdapter(itemsList: MutableList<Item>): RecyclerView.Adapter<MainViewHolder>() {

    interface Callbacks {
        fun onEventSelected(id: Int)
        fun onNoteSelected(id: Int)
    }

    private val NO_ITEM_TYPE: Int = 0
    private val EVENT_HEADER_TYPE: Int = 1
    private val EVENT_ITEM_TYPE: Int = 2
    private val NOTE_HEADER_TYPE: Int = 3
    private val NOTE_ITEM_TYPE: Int = 4
    private val mItemList: MutableList<Item> = itemsList

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainViewHolder {
        val inflater = LayoutInflater.from(parent.context)

        return when (viewType) {
            EVENT_ITEM_TYPE -> {
                val eventView: View = inflater.inflate(R.layout.cell_event, parent, false)
                MainViewHolder(eventView)
            }
            NOTE_ITEM_TYPE -> {
                val todoView: View = inflater.inflate(R.layout.cell_note, parent, false)
                MainViewHolder(todoView)
            }
            EVENT_HEADER_TYPE -> {
                val eventHeaderView: View =
                    inflater.inflate(R.layout.cell_event_header, parent, false)
                MainViewHolder(eventHeaderView)
            }
            NOTE_HEADER_TYPE -> {
                val favoriteHeaderView: View =
                    inflater.inflate(R.layout.cell_note_header, parent, false)
                MainViewHolder(favoriteHeaderView)
            }
            NO_ITEM_TYPE -> throw Exception("Item type mismatch") //TODO: вставить заглушку
            else -> throw Exception("Item type mismatch") //TODO: вставить заглушку
        }
    }

    override fun getItemCount(): Int {
        return mItemList.count()
    }

    override fun getItemViewType(position: Int): Int {
        return when (mItemList[position]) {
            is Event -> EVENT_ITEM_TYPE
            is Note -> NOTE_ITEM_TYPE
            is EventsHeader -> EVENT_HEADER_TYPE
            is NotesHeader -> NOTE_HEADER_TYPE
            else -> NO_ITEM_TYPE
        }
    }

    override fun onBindViewHolder(holder: MainViewHolder, position: Int) {
        val item = mItemList[position]
        if(item is Event || item is Note) {
            holder.itemView.setOnClickListener {
                if (item is Event) (it.context as Callbacks).onEventSelected(item.id)
                if (item is Note) (it.context as Callbacks).onNoteSelected(item.id)
            }
            holder.bind(item)
        }
    }

}