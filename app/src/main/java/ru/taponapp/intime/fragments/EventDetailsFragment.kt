package ru.taponapp.intime.fragments

import android.app.AlertDialog
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.FrameLayout
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.floatingactionbutton.FloatingActionButton
import ru.taponapp.intime.R
import ru.taponapp.intime.models.Event
import ru.taponapp.intime.models.EventDetailsViewModel
import java.text.SimpleDateFormat
import java.util.Calendar

private const val EVENT_ID = "event_id"
private const val DIALOG_DATE = "DialogDate"
private const val DIALOG_TIME = "DialogTime"
private const val REQUEST_DATE = 0
private const val REQUEST_TIME = 1
private const val FULL_DATE_PATTERN = "dd.MM.yy"
private const val SHORT_DATE_PATTERN = "d MMM"
private const val TIME_PATTERN = "HH:mm"

class EventDetailsFragment : Fragment(),
    DatePickerFragment.Callbacks, TimePickerFragment.Callbacks {

    private var initialID: Int? = null
    private var event: Event = Event()
    private lateinit var titleText: EditText
    private lateinit var dateField: FrameLayout
    private lateinit var dateText: TextView
    private lateinit var timeField: FrameLayout
    private lateinit var timeText: TextView
    private lateinit var detailsText: TextView
    private lateinit var deleteEventFab: FloatingActionButton
    private val calendar = Calendar.getInstance()
    private val fullDateFormat = SimpleDateFormat(FULL_DATE_PATTERN)
    private val shortDateFormat = SimpleDateFormat(SHORT_DATE_PATTERN)
    private val myTimeFormat = SimpleDateFormat(TIME_PATTERN)
    private val eventDetailsViewModel: EventDetailsViewModel by lazy {
        ViewModelProvider(this).get(EventDetailsViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initialID = arguments?.getSerializable(EVENT_ID) as Int?
        if (initialID != null) {
            eventDetailsViewModel.loadEvent(initialID)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val eventFragmentView = inflater.inflate(R.layout.fragment_event_details, container, false)
        titleText = eventFragmentView.findViewById(R.id.details_title_text)
        dateField = eventFragmentView.findViewById(R.id.event_date_field)
        dateText = eventFragmentView.findViewById(R.id.event_date_text)
        timeField = eventFragmentView.findViewById(R.id.event_time_field)
        timeText = eventFragmentView.findViewById(R.id.event_time_text)
        detailsText = eventFragmentView.findViewById(R.id.event_details_text)
        deleteEventFab = eventFragmentView.findViewById(R.id.delete_event_fab)
        return eventFragmentView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        dateText.hint = fullDateFormat.format(calendar.time)
        timeText.hint = myTimeFormat.format(calendar.time)
        if (initialID != null) {
            eventDetailsViewModel.eventLiveData.observe(
                viewLifecycleOwner,
                Observer { event ->
                    event.let {
                        this.event = event
                        calendar.timeInMillis = event.timeInMillisSinceEpoch
                        updateUI()
                    }
                })
        }
    }

    override fun onStart() {
        super.onStart()

        val titleWatcher = object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                event.title = s.toString()
            }

            override fun afterTextChanged(s: Editable?) {
            }
        }

        val detailsWatcher = object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                event.details = s.toString()
            }

            override fun afterTextChanged(s: Editable?) {
            }
        }

        titleText.addTextChangedListener(titleWatcher)
        detailsText.addTextChangedListener(detailsWatcher)

        dateField.setOnClickListener {
            DatePickerFragment().apply {
                setTargetFragment(this@EventDetailsFragment, REQUEST_DATE)
                show(this@EventDetailsFragment.parentFragmentManager, DIALOG_DATE)
            }
        }

        timeField.setOnClickListener {
            TimePickerFragment().apply {
                setTargetFragment(this@EventDetailsFragment, REQUEST_TIME)
                    show(this@EventDetailsFragment.parentFragmentManager, DIALOG_TIME)
            }
        }

        deleteEventFab.setOnClickListener {
            MaterialAlertDialogBuilder(requireContext(), R.style.MyAlertDialogStyle).apply {
                setTitle(R.string.delete_event_title)
                setMessage(R.string.confirm_delete_message)
                setPositiveButton(R.string.yes) { dialog, which ->
                    parentFragmentManager.popBackStack()
                    eventDetailsViewModel.deleteEvent(event)
                }
                setNegativeButton(R.string.no) { dialog, which ->
                    dialog.cancel()
                }
                show()
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        if (event.title != "") {
            event.timeInMillisSinceEpoch = calendar.timeInMillis
            if (initialID == null) {
                eventDetailsViewModel.addEvent(event)
            } else {
                eventDetailsViewModel.updateEvent(event)
            }
        }
    }

    override fun onDetach() {
        super.onDetach()
    }

    override fun onDateSelected(date: Calendar) {
//        val dateStringValue = fullDateFormat.format(date.time)
        dateText.setText(fullDateFormat.format(date.time))
        event.date = shortDateFormat.format(date.time)
        calendar.set(Calendar.YEAR, date.get(Calendar.YEAR))
        calendar.set(Calendar.MONTH, date.get(Calendar.MONTH))
        calendar.set(Calendar.DAY_OF_MONTH, date.get(Calendar.DAY_OF_MONTH))
    }

    override fun onTimeSelected(time: Calendar) {
        val timeStringValue = myTimeFormat.format(time.time)
        timeText.setText(timeStringValue)
        event.time = timeStringValue
        calendar.set(Calendar.HOUR_OF_DAY, time.get(Calendar.HOUR_OF_DAY))
        calendar.set(Calendar.MINUTE, time.get(Calendar.MINUTE))
    }

    // Just updating string values of date and time in its fields
    // without setting it in its picker fragments
    fun updateUI() {
        titleText.setText(event.title)
        detailsText.setText(event.details)
        dateText.setText(fullDateFormat.format(event.timeInMillisSinceEpoch))
        if (event.time != "") {
            timeText.setText(event.time)
        }
    }

    companion object {
        fun newInstance(eventID: Int?) : EventDetailsFragment {
            val args = Bundle().apply {
                putSerializable(EVENT_ID, eventID)
            }
            return EventDetailsFragment().apply {
                arguments = args
            }
        }
    }
}