package com.ericwei.selfiediary.ui

import android.os.Bundle
import androidx.preference.Preference
import androidx.preference.PreferenceFragmentCompat
import com.ericwei.selfiediary.R

class SettingsFragment : PreferenceFragmentCompat(), Preference.OnPreferenceClickListener,
    Preference.OnPreferenceChangeListener {

    private lateinit var mTimePickerPref: Preference

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.preferences, rootKey)

        mTimePickerPref = preferenceManager.findPreference<Preference>("time_picker")!!
        mTimePickerPref.onPreferenceClickListener = this
    }

    override fun onPreferenceChange(preference: Preference?, newValue: Any?): Boolean {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onPreferenceClick(preference: Preference?): Boolean {
        when (preference!!.key) {
            "time_picker" -> {
                TimePickerFragment().show(fragmentManager!!, "timePicker")
            }
        }
        return true
    }

}
