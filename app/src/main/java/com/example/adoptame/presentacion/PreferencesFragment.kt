package com.example.adoptame.presentacion

import android.os.Bundle
import android.widget.Toast
import androidx.preference.ListPreference
import androidx.preference.Preference
import androidx.preference.PreferenceFragmentCompat
import androidx.preference.PreferenceManager
import com.example.adoptame.R

class PreferencesFragment : PreferenceFragmentCompat(), Preference.OnPreferenceChangeListener {

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        addPreferencesFromResource(R.xml.preferences)
    }

    private fun bindPreferenceSummaryToValue(preference: Preference) {
        preference.onPreferenceChangeListener = this

        onPreferenceChange(
            preference,
            PreferenceManager
                .getDefaultSharedPreferences(preference.context)
                .getString(preference.key, "prefsData")
        )
    }

    override fun onPreferenceChange(preference: Preference, value: Any?): Boolean {
        val stringValue = value.toString()

        if (preference is ListPreference) {
            val prefIndex = preference.findIndexOfValue(stringValue)
            if (prefIndex >= 0) {
                preference.setSummary(preference.entries[prefIndex])
            }
        } else {
            preference?.summary = stringValue
        }
        return true
    }

    override fun onPreferenceTreeClick(preference: Preference): Boolean {
        return when (preference.key) {
            activity?.resources?.getResourceEntryName(R.string.catchnewsapi) -> {
                Toast.makeText(
                    this.context,
                    "${resources.getString(R.string.catchnewsapi)} seleccionada",
                    Toast.LENGTH_SHORT
                ).show()
                true
            }
            activity?.resources?.getResourceEntryName(R.string.newsapi) -> {
                Toast.makeText(
                    this.context,
                    "${resources.getString(R.string.newsapi)} seleccionada",
                    Toast.LENGTH_SHORT
                )
                    .show()
                true
            }
            else -> {
                super.onPreferenceTreeClick(preference)
            }
        }
    }

}