package com.edrees.newsapp.network

import com.edrees.newsapp.model.ArticleResponse

interface ArticleRemoteDataSource {
    suspend fun getTopHeadlinesByCountry(country: String, apiKey: String): ArticleResponse
}