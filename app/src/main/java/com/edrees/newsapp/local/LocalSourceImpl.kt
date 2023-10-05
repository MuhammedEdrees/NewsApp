package com.edrees.newsapp.local

import android.content.Context
import com.edrees.newsapp.dao.ArticleDao
import com.edrees.newsapp.db.NewsDatabase
import com.edrees.newsapp.model.Article

class LocalSourceImpl(private val articleDao: ArticleDao): LocalSource {
    override suspend fun insertArticle(vararg articles: Article) {
        articleDao.insertArticle(*articles)
    }

    override suspend fun getAllArticles() = articleDao.getAllArticles()

    override suspend fun deleteArticle(article: Article) = articleDao.deleteArticle(article)

    override suspend fun checkIfExists(url: String) = articleDao.checkIfExists(url) == 1
}