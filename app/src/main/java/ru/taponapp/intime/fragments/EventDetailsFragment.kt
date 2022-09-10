package ru.taponapp.intime.fragments

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import ru.taponapp.intime.R
import ru.taponapp.intime.activities.MainActivity
import ru.taponapp.intime.databinding.FragmentEventDetailsBinding
import ru.taponapp.intime.models.Event
import ru.taponapp.intime.models.EventDetailsViewModel
import java.text.SimpleDateFormat
import java.util.*

private const val EVENT_ID = "event_id"
private const val FULL_DATE_PATTERN = "dd.MM.yy"
private const val SHORT_DATE_PATTERN = "d MMMM"
private const val TIME_PATTERN = "HH:mm"

class EventDetailsFragment : Fragment() {

    private var event: Event = Event()
    private var initialID: Int? = null

    private var _binding: FragmentEventDetailsBinding? = null
    private val binding get() = _binding!!

    private val calendar = Calendar.getInstance()
    private val fullDateFormat = SimpleDateFormat(FULL_DATE_PATTERN, Locale.getDefault())
    private val shortDateFormat = SimpleDateFormat(SHORT_DATE_PATTERN, Locale.getDefault())
    private val myTimeFormat = SimpleDateFormat(TIME_PATTERN, Locale.getDefault())

    private val eventDetailsViewModel: EventDetailsViewModel by viewModels()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initialID = arguments?.getSerializable(EVENT_ID) as Int?

        if (initialID != null) {
            eventDetailsViewModel.loadEvent(initialID!!)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentEventDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        binding.dateText.hint = fullDateFormat.format(calendar.timeInMillis)
        binding.timeText.hint = myTimeFormat.format(calendar.timeInMillis)


        if (initialID != null) {
            eventDetailsViewModel.eventLiveData.observe(viewLifecycleOwner, { event ->
                this.event = event
                calendar.timeInMillis = event.timeInMillisSinceEpoch
                updateUI()
            })
        }
    }

    override fun onStart() {
        super.onStart()

        binding.titleText.addTextChangedListener {
            event.title = it.toString()
        }

        binding.detailsField.eventDetailsText.addTextChangedListener {
            event.details = it.toString()
        }

        binding.dateField.setOnClickListener {
            showDatePicker()
        }

        binding.timeField.setOnClickListener {
            showTimePicker()
        }

        binding.saveEventBtn.setOnClickListener {
            if (event.title != "") {
                event.timeInMillisSinceEpoch = calendar.timeInMillis
                if (initialID == null) {
                    eventDetailsViewModel.addEvent(event)
                } else {
                    eventDetailsViewModel.updateEvent(event)
                }
                parentFragmentManager.popBackStack()
            }
        }

        binding.deleteEventBtn.setOnClickListener {
            MaterialAlertDialogBuilder(requireContext(), R.style.MyAlertDialogStyle).apply {
                setTitle(R.string.delete_event_title)
                setMessage(R.string.confirm_delete_message)
                setPositiveButton(R.string.yes) { _, _ ->
                    eventDetailsViewModel.deleteEvent(event)
                    parentFragmentManager.popBackStack()
                }
                setNegativeButton(R.string.no) { dialog, _ ->
                    dialog.cancel()
                }
                show()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun showDatePicker() {

        val listener = DatePickerDialog.OnDateSetListener { _, year, month, day ->
            calendar.set(Calendar.YEAR, year)
            calendar.set(Calendar.MONTH, month)
            calendar.set(Calendar.DAY_OF_MONTH, day)

            binding.dateText.text = fullDateFormat.format(calendar.timeInMillis)

//            // With russian system language date looks like "1 сент." instead of "1 Сен"
//            val shortDate: String = shortDateFormat.format(calendar.timeInMillis)
//            event.date = shortDate.substringBefore(" ") + " " +
//                    shortDate.substringAfter(" ").take(3).capitalize()
            // With russian system language date looks like "1 сент." instead of "1 Сен"
            event.date = shortDateFormat.format(calendar.timeInMillis)
        }

        DatePickerDialog(
            requireContext(),
            R.style.MyDatePickerStyle,
            listener,
            calendar.get(Calendar.YEAR),
            calendar.get(Calendar.MONTH),
            calendar.get(Calendar.DAY_OF_MONTH)
        ).show()
    }

    private fun showTimePicker() {

        val listener = TimePickerDialog.OnTimeSetListener { _, hour, minute ->
            calendar.set(Calendar.HOUR_OF_DAY, hour)
            calendar.set(Calendar.MINUTE, minute)

            event.time = myTimeFormat.format(calendar.time)
            binding.timeText.text = myTimeFormat.format(calendar.time)
        }

        TimePickerDialog(
            requireContext(),
            R.style.MyTimePickerStyle,
            listener,
            calendar.get(Calendar.HOUR_OF_DAY),
            calendar.get(Calendar.MINUTE),
            true
        ).show()
    }

    // Just updating string values of date and time in its fields
    // without setting it in its picker fragments
    private fun updateUI() {
        binding.titleText.setText(event.title)
        binding.detailsField.eventDetailsText.setText(event.details)
        binding.dateText.text = fullDateFormat.format(event.timeInMillisSinceEpoch)
        if (event.time != "") {
            binding.timeText.text = event.time
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