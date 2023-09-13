package com.edrees.newsapp.ui.settings

import android.os.Bundle
import androidx.appcompat.app.AppCompatDelegate
import androidx.preference.DropDownPreference
import androidx.preference.ListPreference
import androidx.preference.PreferenceFragmentCompat
import com.edrees.newsapp.R
import com.edrees.newsapp.util.Constants

class SettingsFragment : PreferenceFragmentCompat() {

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.root_preferences, rootKey)
        val countryPreference = findPreference<ListPreference>("country")
        countryPreference?.setOnPreferenceChangeListener { _, newValue ->
            // Update the value in the Constants object
            Constants.setCountry(newValue as String)
            true
        }
        val languagePreference = findPreference<ListPreference>("language")
        languagePreference?.setOnPreferenceChangeListener { _, newValue ->
            // Update the value in the Constants object
            Constants.setLanguage(newValue as String)
            true
        }
        val darkModePreference = findPreference<DropDownPreference>("dark_mode")
        darkModePreference?.setOnPreferenceChangeListener{_, newValue ->
            when(newValue as String){
                "default" -> {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM)
                }
                "on" -> {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                }
                "off" -> {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                }
            }
            true
        }
//        val fontSizePreference = findPreference<DropDownPreference>("font_size")
//        fontSizePreference?.setOnPreferenceChangeListener{_, newValue ->
//            when(newValue as String) {
//                "small"-> {
//                    (activity as MainActivity).onThemeChanged(R.style.FontSizeSmall)
//                }
//                "medium"-> {
//                    (activity as MainActivity).onThemeChanged(R.style.FontSizeMedium)
//                }
//                "large"-> {
//                    (activity as MainActivity).onThemeChanged(R.style.FontSizeLarge)
//                }
//            }
//            true
//        }
    }
}