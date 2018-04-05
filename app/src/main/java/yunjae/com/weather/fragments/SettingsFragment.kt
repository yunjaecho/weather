package yunjae.com.weather.fragments

import android.content.SharedPreferences
import android.os.Bundle
import android.preference.Preference
import android.preference.PreferenceFragment
import android.preference.EditTextPreference
import android.preference.SwitchPreference
import android.preference.PreferenceManager
import yunjae.com.weather.R
import android.preference.ListPreference
import yunjae.com.weather.WeatherActivity
import android.content.Intent
import android.view.MenuItem


class SettingsFragment: PreferenceFragment(), Preference.OnPreferenceChangeListener, SharedPreferences.OnSharedPreferenceChangeListener  {

    private var preferences: SharedPreferences? = null
    private var geolocationEnabledPreference: SwitchPreference? = null
    private var manualLocationPreference: EditTextPreference? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        addPreferencesFromResource(R.xml.app_preferences)

        preferences = PreferenceManager.getDefaultSharedPreferences(activity)

        geolocationEnabledPreference = findPreference(getString(R.string.pref_geolocation_enabled)) as SwitchPreference
        manualLocationPreference = findPreference(getString(R.string.pref_manual_location)) as EditTextPreference

        bindPreferenceSummaryToValue(manualLocationPreference)
        bindPreferenceSummaryToValue(findPreference(getString(R.string.pref_temperature_unit)))

        PreferenceManager.getDefaultSharedPreferences(activity).registerOnSharedPreferenceChangeListener(this)

        onSharedPreferenceChanged(null, null)

        if (preferences?.getBoolean(getString(R.string.pref_needs_setup), false) == false) {
            val editor = preferences?.edit()
            editor?.putBoolean(getString(R.string.pref_needs_setup), false)
            editor?.apply()
        }

        setHasOptionsMenu(true)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.getItemId()
        if (id == android.R.id.home) {
            startActivity(Intent(activity, WeatherActivity::class.java))
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onPreferenceChange(preference: Preference?, value: Any?): Boolean {
        val stringValue = value.toString()

        if (preference is ListPreference) {
            val index = preference.findIndexOfValue(stringValue)

            preference.setSummary(if (index >= 0) preference.entries[index] else null)

        } else if (preference is EditTextPreference) {
            preference.setSummary(stringValue)
        }

        return true
    }

    override fun onSharedPreferenceChanged(sharedPreferences: SharedPreferences?, s: String?) {
        if (geolocationEnabledPreference!!.isChecked()) {
            manualLocationPreference?.setEnabled(false)
        } else {
            manualLocationPreference?.setEnabled(true)
        }
    }

    private fun bindPreferenceSummaryToValue(preference: Preference?) {
        preference?.onPreferenceChangeListener = this
        onPreferenceChange(preference, preferences?.getStringSet(preference?.key, null))
    }
}