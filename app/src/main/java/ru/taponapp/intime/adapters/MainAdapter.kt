package ru.taponapp.intime.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import ru.taponapp.intime.R
import ru.taponapp.intime.models.*
import java.lang.Exception

class MainAdapter: RecyclerView.Adapter<MainAdapter.MainViewHolder>() {
    private val NO_ITEM_TYPE: Int = 0
    private val EVENT_HEADER_TYPE: Int = 1
    private val EVENT_ITEM_TYPE: Int = 2
    private val FAVORITE_HEADER_TYPE: Int = 3
    private val FAVORITE_ITEM_TYPE: Int = 4

    private var mItemList: MutableList<Item> = MainViewModel().getItemslist()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return when (viewType) {
            EVENT_ITEM_TYPE -> {
                val eventView: View = inflater.inflate(R.layout.cell_event, parent, false)
                MainViewHolder(eventView)
            }
            FAVORITE_ITEM_TYPE -> {
                val favoriteView: View = inflater.inflate(R.layout.cell_todo, parent, false)
                MainViewHolder(favoriteView)
            }
            EVENT_HEADER_TYPE -> {
                val eventHeaderView: View =
                    inflater.inflate(R.layout.cell_event_header, parent, false)
                MainViewHolder(eventHeaderView)
            }
            FAVORITE_HEADER_TYPE -> {
                val favoriteHeaderView: View =
                    inflater.inflate(R.layout.cell_todo_header, parent, false)
                MainViewHolder(favoriteHeaderView)
            }
            else -> throw Exception("Item type mismatch") //TODO: вставить заглушку
        }
    }

    override fun getItemCount(): Int {
        return mItemList.count()
    }

    override fun getItemViewType(position: Int): Int {
        return when (mItemList[position]) {
            is Event -> EVENT_ITEM_TYPE
            is Todo -> FAVORITE_ITEM_TYPE
            is EventHeader -> EVENT_HEADER_TYPE
            is TodoHeader -> FAVORITE_HEADER_TYPE
            else -> NO_ITEM_TYPE
        }
    }

    override fun onBindViewHolder(holder: MainViewHolder, position: Int) {
        val item = mItemList[position]
        holder.bind(item)
    }

    class MainViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val eventItemText: TextView? = itemView.findViewById(R.id.event_text)
        private val favoriteItemText: TextView? = itemView.findViewById(R.id.todo_text)
        private lateinit var item: Item

        fun bind(item: Item) {
            this.item = item
            when (item) {
                is Event -> eventItemText?.text = item.title
                is Todo -> favoriteItemText?.text = item.todoTitle
            }
        }
    }

//    fun setData(newItemsList: List<Item>) {
//        mItemList.clear()
//        mItemList.addAll(newItemsList)
//    }

    fun addItem() {
        val position = 2
        mItemList.add(position, Event(title = "123"))
        notifyItemInserted(position)
    }
}
