package com.edrees.newsapp.ui.categories.categorized_news

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.edrees.newsapp.model.Article
import com.edrees.newsapp.repo.ArticleRepository
import kotlinx.coroutines.launch

class CategorizedNewsViewModel(val repo: ArticleRepository): ViewModel() {
    private val _listOfArticles = MutableLiveData<List<Article>>()
    val listOfArticles: LiveData<List<Article>> = _listOfArticles
    val apiKey = "cfb9fb4a523748199c5f64423f1ef4ed"
    fun getCategorizedArticles(category: String){
        viewModelScope.launch {
            _listOfArticles.value = repo.getTopHeadlinesByCategory(category, apiKey).articles
        }
    }
}