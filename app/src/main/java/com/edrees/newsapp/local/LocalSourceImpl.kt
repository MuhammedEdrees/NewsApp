package com.edrees.newsapp.local

import android.content.Context
import com.edrees.newsapp.db.NewsDatabase
import com.edrees.newsapp.model.Article

class LocalSourceImpl(context: Context): LocalSource {
    private val articleDao = NewsDatabase.getInstance(context).articleDao()
    override suspend fun insertArticle(vararg articles: Article) {
        articleDao.insertArticle(*articles)
    }
}