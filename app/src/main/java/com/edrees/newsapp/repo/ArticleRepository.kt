package com.edrees.newsapp.repo

import com.edrees.newsapp.model.Article
import com.edrees.newsapp.model.ArticleResponse

interface ArticleRepository {
    suspend fun getTopHeadlines(language: String, country: String, apiKey: String): ArticleResponse
    suspend fun getTopHeadlinesByCategory(category: String, apiKey: String): ArticleResponse
    suspend fun getQuerySearchResult(query: String, apiKey: String, lang: String, page: Int): ArticleResponse
    suspend fun insertLocalArticle(vararg articles: Article)
    suspend fun getLocalArticles(): List<Article>
    suspend fun deleteLocalArticle(article: Article)
    suspend fun checkIfExistsLocally(article: Article): Boolean
}