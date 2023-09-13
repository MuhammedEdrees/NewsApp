package com.edrees.newsapp.util

object Constants {
    val apiKey = "cfb9fb4a523748199c5f64423f1ef4ed"
    private var _country = "us"
    val country: String
        get() = _country
    private var _language = "en"
    val language: String
        get() = _language
    fun setCountry(country: String) {
        _country = country
    }
    fun setLanguage(language: String){
        _language = language
    }
    val repoURL = ""
}