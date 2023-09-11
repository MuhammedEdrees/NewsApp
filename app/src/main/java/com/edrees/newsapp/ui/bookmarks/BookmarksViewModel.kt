package com.edrees.newsapp.ui.bookmarks

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.edrees.newsapp.model.Article
import com.edrees.newsapp.repo.ArticleRepository
import kotlinx.coroutines.launch

class BookmarksViewModel(private val repo: ArticleRepository) : ViewModel() {
    private val _listOfArticles = MutableLiveData<List<Article>>()
    val listOfArticles: LiveData<List<Article>> = _listOfArticles
    fun getBookmarks(){
        viewModelScope.launch {
            _listOfArticles.value = repo.getLocalArticles()
        }
    }
    fun deleteBookmark(position: Int){
        viewModelScope.launch {
            repo.deleteLocalArticle(listOfArticles.value!!.get(position))
            val list = listOfArticles.value!!.toMutableList()
            list.removeAt(position)
            _listOfArticles.value = list
        }
    }
}