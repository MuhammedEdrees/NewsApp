package com.edrees.newsapp.network

import com.edrees.newsapp.model.ArticleResponse
import com.edrees.newsapp.util.Constants.apiKey
import com.edrees.newsapp.util.Constants.country
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ArticleService {
    @GET("top-headlines")
    suspend fun getTopHeadlines(@Query("language") language: String, @Query("country") country: String, @Query("apiKey") apiKey: String): ArticleResponse

    @GET("top-headlines")
    suspend fun getTopHeadlinesByCategory(@Query("category") category: String, @Query("apiKey") apiKey: String): ArticleResponse
    @GET("everything")
    suspend fun getQuerySearchResult(@Query("q") query: String, @Query("apiKey") apiKey: String, @Query("language") lang: String, @Query("page") page: Int): ArticleResponse
}