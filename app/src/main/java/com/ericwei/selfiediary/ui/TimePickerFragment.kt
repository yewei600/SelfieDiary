package com.ericwei.selfiediary.ui

import android.app.Dialog
import android.app.TimePickerDialog
import android.os.Bundle
import android.preference.PreferenceManager
import android.text.format.DateFormat
import android.widget.TimePicker
import androidx.fragment.app.DialogFragment
import com.ericwei.selfiediary.R
import java.util.*

class TimePickerFragment : DialogFragment(), TimePickerDialog.OnTimeSetListener {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        // Use the current time as the default values for the picker
        val c = Calendar.getInstance()
        val hour = c.get(Calendar.HOUR_OF_DAY)
        val minute = c.get(Calendar.MINUTE)

        // Create a new instance of TimePickerDialog and return it
        return TimePickerDialog(activity, this, hour, minute, DateFormat.is24HourFormat(activity))
    }

    override fun onTimeSet(p0: TimePicker?, hourOfDay: Int, minute: Int) {
        val sharedPref = PreferenceManager.getDefaultSharedPreferences(context)
        with(sharedPref.edit()) {
            putString(
                getString(R.string.saved_notification_time),
                "$hourOfDay:$minute"
            )
            commit()
        }
    }
}