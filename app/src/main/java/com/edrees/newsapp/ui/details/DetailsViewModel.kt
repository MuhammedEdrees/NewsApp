package com.edrees.newsapp.ui.details

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.edrees.newsapp.model.Article
import com.edrees.newsapp.repo.ArticleRepository
import kotlinx.coroutines.launch

class DetailsViewModel (private val repo: ArticleRepository): ViewModel(){
    private val _isFavorite = MutableLiveData<Boolean>()
    val isFavorite: LiveData<Boolean> = _isFavorite
    fun checkIfFavorite(article: Article){
        viewModelScope.launch {
            _isFavorite.value = repo.checkIfExistsLocally(article)
            Log.d("seedre", "Executed Check")
        }
    }

    fun addFavorite(article: Article) {
        viewModelScope.launch {
            repo.insertLocalArticle(article)
            _isFavorite.value = true
        }
    }
    fun deleteFavorite(article: Article){
        viewModelScope.launch {
            repo.deleteLocalArticle(article)
            _isFavorite.value = false
        }
    }
}