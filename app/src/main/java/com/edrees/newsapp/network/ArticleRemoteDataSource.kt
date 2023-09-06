package com.edrees.newsapp.network

import com.edrees.newsapp.model.ArticleResponse
import com.edrees.newsapp.model.Category

interface ArticleRemoteDataSource {
    suspend fun getTopHeadlinesByCountry(country: String, apiKey: String): ArticleResponse
    suspend fun getTopHeadlinesByCategory(category: String, apiKey: String): ArticleResponse
}