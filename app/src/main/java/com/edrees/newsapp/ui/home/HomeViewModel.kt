package com.edrees.newsapp.ui.home

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.edrees.newsapp.model.Article
import com.edrees.newsapp.model.ArticleResponse
import com.edrees.newsapp.repo.ArticleRepository
import com.edrees.newsapp.util.Constants
import com.edrees.newsapp.util.Constants.apiKey
import com.edrees.newsapp.util.Constants.country
import com.edrees.newsapp.util.Constants.language
import kotlinx.coroutines.launch

class HomeViewModel(private val repo: ArticleRepository) : ViewModel() {
    private val _listOfArticles = MutableLiveData<List<Article>>()
    val listOfArticle: LiveData<List<Article>> = _listOfArticles
    fun getTopHeadlines(){
        viewModelScope.launch {
            val response= repo.getTopHeadlines(language, country, apiKey).articles
            Log.d("sedree", "Response for $country, $language, $apiKey: ${response}")
            _listOfArticles.value = response
        }
    }

}