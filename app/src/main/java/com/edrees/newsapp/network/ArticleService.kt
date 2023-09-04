package com.edrees.newsapp.network

import com.edrees.newsapp.model.ArticleResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ArticleService {
    @GET("top-headlines")
    suspend fun getTopHeadlinesByCountry(@Query("country") country: String, @Query("apiKey") apiKey: String): ArticleResponse
}