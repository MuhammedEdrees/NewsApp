package com.edrees.newsapp.ui.search

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.edrees.newsapp.model.Article
import com.edrees.newsapp.repo.ArticleRepository
import kotlinx.coroutines.launch

class SearchViewModel(private val repo: ArticleRepository) : ViewModel() {
    private val apiKey = "cfb9fb4a523748199c5f64423f1ef4ed"
    private val _listOfArticle = MutableLiveData<List<Article>>()
    val listOfArticles: LiveData<List<Article>> = _listOfArticle
    fun search(query: String, lang: String, page: Int){
        viewModelScope.launch{
            _listOfArticle.value = repo.getQuerySearchResult(query, apiKey, lang, page).articles
        }
    }
}