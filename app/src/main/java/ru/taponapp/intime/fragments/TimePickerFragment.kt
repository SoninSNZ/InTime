package ru.taponapp.intime.fragments

import android.app.Dialog
import android.app.TimePickerDialog
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import ru.taponapp.intime.R
import java.text.SimpleDateFormat
import java.util.*

var timeCalendar = Calendar.getInstance()

class TimePickerFragment : DialogFragment() {

    interface Callbacks {
        fun onTimeSelected(time: Calendar)
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {

        val initialHour: Int = timeCalendar.get(Calendar.HOUR)
        val initialMinute: Int = timeCalendar.get(Calendar.MINUTE)

        val listener = TimePickerDialog.OnTimeSetListener { view, hour, minute ->
            timeCalendar.set(Calendar.HOUR_OF_DAY, hour)
            timeCalendar.set(Calendar.MINUTE, minute)

            targetFragment.let { fragment ->
                (fragment as Callbacks).onTimeSelected(timeCalendar)
            }
        }

        return TimePickerDialog(
            context,
            R.style.MyTimePickerStyle,
            listener,
            initialHour,
            initialMinute,
            true
        )
    }
}