package com.edrees.newsapp.repo

import com.edrees.newsapp.model.Article
import com.edrees.newsapp.model.ArticleResponse

interface ArticleRepository {
    suspend fun getTopHeadlinesByCountry( country: String, apiKey: String): ArticleResponse
    suspend fun insertArticle(vararg articles: Article)
}