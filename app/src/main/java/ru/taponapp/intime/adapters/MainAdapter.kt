package ru.taponapp.intime.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.RecyclerView
import ru.taponapp.intime.R
import ru.taponapp.intime.fragments.MainFragment
import ru.taponapp.intime.models.*
import kotlin.Exception

class MainAdapter(itemsList: MutableList<Item>): RecyclerView.Adapter<MainAdapter.MainViewHolder>() {
    private val NO_ITEM_TYPE: Int = 0
    private val EVENT_HEADER_TYPE: Int = 1
    private val EVENT_ITEM_TYPE: Int = 2
    private val TODO_HEADER_TYPE: Int = 3
    private val TODO_ITEM_TYPE: Int = 4

    var mItemList: MutableList<Item> = itemsList

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return when (viewType) {
            EVENT_ITEM_TYPE -> {
                val eventView: View = inflater.inflate(R.layout.cell_event, parent, false)
                MainViewHolder(eventView)
            }
            TODO_ITEM_TYPE -> {
                val favoriteView: View = inflater.inflate(R.layout.cell_todo, parent, false)
                MainViewHolder(favoriteView)
            }
            EVENT_HEADER_TYPE -> {
                val eventHeaderView: View =
                    inflater.inflate(R.layout.cell_event_header, parent, false)
                MainViewHolder(eventHeaderView)
            }
            TODO_HEADER_TYPE -> {
                val favoriteHeaderView: View =
                    inflater.inflate(R.layout.cell_todo_header, parent, false)
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
            is Todo -> TODO_ITEM_TYPE
            is EventHeader -> EVENT_HEADER_TYPE
            is TodoHeader -> TODO_HEADER_TYPE
            else -> NO_ITEM_TYPE
        }
    }

    override fun onBindViewHolder(holder: MainViewHolder, position: Int) {
        val item = mItemList[position]
        holder.bind(item)
    }

    class MainViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val eventItemText: TextView? = itemView.findViewById(R.id.event_text)
        private val todoItemText: TextView? = itemView.findViewById(R.id.todo_text)
        private lateinit var item: Item

        fun bind(item: Item) {
            this.item = item
            when (item) {
                is Event -> eventItemText?.text = item.title
                is Todo -> todoItemText?.text = item.todoTitle
            }
        }
    }
}
