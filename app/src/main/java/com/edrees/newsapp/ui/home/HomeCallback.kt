package com.edrees.newsapp.ui.home

import com.edrees.newsapp.model.Article

interface HomeCallback {
    fun navigateToDetails(article: Article)
}