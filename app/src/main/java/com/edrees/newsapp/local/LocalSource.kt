package com.edrees.newsapp.local

import androidx.room.Delete
import androidx.room.Query
import com.edrees.newsapp.model.Article

interface LocalSource {
    suspend fun insertArticle(vararg articles: Article)
    suspend fun getAllArticles(): List<Article>
    suspend fun deleteArticle(article: Article)
    suspend fun checkIfExists(url: String): Boolean
}