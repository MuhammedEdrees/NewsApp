package com.edrees.newsapp.ui.home

import com.edrees.newsapp.model.Article

interface DetailsCallback {
    fun navigateToDetails(article: Article)
}