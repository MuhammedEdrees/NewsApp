package com.edrees.newsapp.network

import com.edrees.newsapp.model.ArticleResponse
import com.edrees.newsapp.model.Category
import retrofit2.http.Query

interface ArticleRemoteDataSource {
    suspend fun getTopHeadlines(language: String, country: String, apiKey: String): ArticleResponse
    suspend fun getTopHeadlinesByCategory(category: String, apiKey: String): ArticleResponse
    suspend fun getQuerySearchResult(query: String, apiKey: String, lang: String, page: Int): ArticleResponse
}