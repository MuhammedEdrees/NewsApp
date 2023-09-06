package com.edrees.newsapp.ui.categories

import com.edrees.newsapp.model.Category

interface CategoryCallback {
    fun navigateToCatgorizedNews(category: Category)
}