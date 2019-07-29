package com.ericwei.selfiediary.ui

import android.content.SharedPreferences
import android.os.Bundle
import androidx.preference.Preference
import androidx.preference.PreferenceFragmentCompat
import androidx.preference.PreferenceManager
import com.ericwei.selfiediary.R

class SettingsFragment : PreferenceFragmentCompat(), Preference.OnPreferenceClickListener,
    SharedPreferences.OnSharedPreferenceChangeListener {

    private lateinit var mTimePickerPref: Preference

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.preferences, rootKey)

        mTimePickerPref = preferenceManager.findPreference<Preference>(getString(R.string.saved_notification_time))!!
        mTimePickerPref.onPreferenceClickListener = this
        PreferenceManager.getDefaultSharedPreferences(context).registerOnSharedPreferenceChangeListener(this)
        // showTimepickerSummary()
    }

    override fun onSharedPreferenceChanged(p0: SharedPreferences?, p1: String?) {
        //showTimepickerSummary()
    }

    override fun onPreferenceClick(preference: Preference?): Boolean {
        when (preference!!.key) {
            getString(R.string.saved_notification_time) -> {
                TimePickerFragment().show(fragmentManager!!, "timePicker")
            }
        }
        return true
    }

//    private fun showTimepickerSummary() {
//        val notifTime = android.preference.PreferenceManager.getDefaultSharedPreferences(context)
//            .getString(context!!.getString(R.string.saved_notification_time), "17:00").split(":")
//        if (notifTime.size == 2) {
//            mTimePickerPref.summary = "Reminder at ${notifTime[0]}:${notifTime[1]}"
//        }
//    }
}
