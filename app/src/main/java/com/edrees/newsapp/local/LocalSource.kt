package com.edrees.newsapp.local

import com.edrees.newsapp.model.Article

interface LocalSource {
    suspend fun insertArticle(vararg articles: Article)
}