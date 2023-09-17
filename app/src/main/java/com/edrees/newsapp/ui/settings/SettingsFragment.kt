package com.edrees.newsapp.ui.settings

import android.app.LocaleManager
import android.content.res.Configuration
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.appcompat.widget.Toolbar
import androidx.preference.DropDownPreference
import androidx.preference.ListPreference
import androidx.preference.PreferenceFragmentCompat
import com.edrees.newsapp.R
import com.edrees.newsapp.util.Constants
import com.edrees.newsapp.util.LocaleHelper
import java.util.Locale

class SettingsFragment : PreferenceFragmentCompat() {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setFragmentTitle(requireContext().getString(R.string.action_settings))
    }
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
        val localePreference = findPreference<ListPreference>("locale")
        localePreference?.setOnPreferenceChangeListener{_, newValue ->
            when(newValue as String){
                "ar_eg" -> {
                    LocaleHelper().setLocale(requireContext(), "ar")
                    activity?.recreate()
                }
                "en_us" -> {
                    LocaleHelper().setLocale(requireContext(), "en")
                    activity?.recreate()
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
    private fun setFragmentTitle(title: String) {
        val activity = requireActivity() as AppCompatActivity
        val toolbar = activity.findViewById<Toolbar>(R.id.toolbar)
        toolbar.title = title
    }
}