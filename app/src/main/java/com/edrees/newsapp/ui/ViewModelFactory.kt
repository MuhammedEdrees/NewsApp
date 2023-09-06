package com.edrees.newsapp.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.edrees.newsapp.repo.ArticleRepository
import com.edrees.newsapp.ui.categories.categorized_news.CategorizedNewsViewModel
import com.edrees.newsapp.ui.home.HomeViewModel

class ViewModelFactory(val repo: ArticleRepository): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return if(modelClass.isAssignableFrom(HomeViewModel::class.java)) {
            HomeViewModel(repo) as T
        } else if(modelClass.isAssignableFrom(CategorizedNewsViewModel::class.java)) {
            CategorizedNewsViewModel(repo) as T
        } else {
            throw IllegalArgumentException("No Matching Viewmodels!")
        }
    }
}