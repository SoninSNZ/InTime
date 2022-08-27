package ru.taponapp.intime.adapters

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.divider.MaterialDivider
import ru.taponapp.intime.R
import ru.taponapp.intime.models.Event
import ru.taponapp.intime.models.Item
import ru.taponapp.intime.models.Note
import java.text.SimpleDateFormat
import java.util.*

class MainViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    private var item: Item = Item()
    private val calendar = Calendar.getInstance()
    private val currentDate = SimpleDateFormat("d MMM").format(calendar.time)
    private val eventTitle: TextView? = itemView.findViewById(R.id.event_title)
    private val eventDate: TextView? = itemView.findViewById(R.id.event_date)
    private val eventTime: TextView? = itemView.findViewById(R.id.event_time)
    private val noteTitle: TextView? = itemView.findViewById(R.id.note_title)
    private val noteText: TextView? = itemView.findViewById(R.id.note_text)
    private val noteDivider: MaterialDivider? = itemView.findViewById(R.id.note_divider)

    fun bind(item: Item) {
        this.item = item
        when (item) {
            is Event -> setupEventView(item)
            is Note -> setupNoteView(item)
        }
    }

    fun setupEventView(event: Event) {
        eventTitle?.setText(event.title)
        eventDate?.setText(event.date)

        if (event.date == currentDate) {
            eventDate?.visibility = View.GONE
        }

        if (event.time != "") {
            eventTime?.setText(event.time)
        } else {
            eventTime?.visibility = View.GONE
            eventDate?.setTextSize(17f)
        }
    }

    fun setupNoteView(note: Note) {
        noteTitle?.setText(note.title)
        noteText?.setText(note.text)

        if (note.text == "") {
            noteText?.visibility = View.GONE
            noteDivider?.visibility = View.GONE
        }

        if (note.title == "") {
            noteTitle?.visibility = View.GONE
            noteDivider?.visibility = View.GONE
        }
    }
}