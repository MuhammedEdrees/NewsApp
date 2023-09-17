package com.edrees.newsapp.ui

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import com.edrees.newsapp.R
import com.edrees.newsapp.util.Constants.repoURL
import mehdi.sakout.aboutpage.AboutPage
import mehdi.sakout.aboutpage.Element

class AboutFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val versionElement = Element().apply {
            title = "Version 1.0.0"
        }
        setFragmentTitle(requireContext().getString(R.string.action_about))
        return AboutPage(requireContext())
            .isRTL(false)
            .setDescription(getString(R.string.app_description))
            .setImage(R.drawable.ic_about_icon)
            .addItem(versionElement)
            .addGroup(getString(R.string.about_connect_group))
            .addEmail("muhammed.edrees101@gmail.com")
            .addWebsite("https://g.dev/edrees/")
            .addGitHub("MuhammedEdrees/NewsApp")
            .create()
    }
    private fun setFragmentTitle(title: String) {
        val activity = requireActivity() as AppCompatActivity
        val toolbar = activity.findViewById<Toolbar>(R.id.toolbar)
        toolbar.title = title
    }
}