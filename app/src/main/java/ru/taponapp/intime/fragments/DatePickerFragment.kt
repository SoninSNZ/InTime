package ru.taponapp.intime.fragments

import android.app.DatePickerDialog
import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import ru.taponapp.intime.R
import java.util.*

var dateCalendar = Calendar.getInstance()

class DatePickerFragment : DialogFragment() {

    interface Callbacks {
        fun onDateSelected(date: Calendar)
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {

        val initialYear: Int = dateCalendar.get(Calendar.YEAR)
        val initialMonth: Int = dateCalendar.get(Calendar.MONTH)
        val initialDay: Int = dateCalendar.get(Calendar.DAY_OF_MONTH)

        val listener = DatePickerDialog.OnDateSetListener { view, year, month, dayOfMonth ->
            dateCalendar.set(Calendar.YEAR, year)
            dateCalendar.set(Calendar.MONTH, month)
            dateCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth)

            targetFragment.let { fragment ->
                (fragment as Callbacks).onDateSelected(date = dateCalendar)
            }
        }

        return DatePickerDialog(
            requireContext(),
            R.style.MyDatePickerStyle,
            listener,
            initialYear,
            initialMonth,
            initialDay
        )
    }
}