package com.edrees.newsapp.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.edrees.newsapp.model.Article
import com.edrees.newsapp.model.ArticleResponse
import com.edrees.newsapp.repo.ArticleRepository
import kotlinx.coroutines.launch

class HomeViewModel(private val repo: ArticleRepository) : ViewModel() {
    private val _listOfArticles = MutableLiveData<List<Article>>()
    val listOfArticle: LiveData<List<Article>> = _listOfArticles
    val country = "us"
    val apiKey = "cfb9fb4a523748199c5f64423f1ef4ed"
    fun getTopHeadlines(){
        viewModelScope.launch {
            _listOfArticles.value = repo.getTopHeadlinesByCountry(country, apiKey).articles
        }
    }

}