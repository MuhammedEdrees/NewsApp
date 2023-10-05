package com.edrees.newsapp.network

import retrofit2.Retrofit

class ArticleRemoteDataSourceImpl(private val retrofit: Retrofit): ArticleRemoteDataSource {
    override suspend fun getTopHeadlines(
        language: String,
        country: String,
        apiKey: String
    ) = retrofit.create(ArticleService::class.java).getTopHeadlines(language, country, apiKey)

    override suspend fun getTopHeadlinesByCategory(
        category: String,
        apiKey: String
    ) = retrofit.create(ArticleService::class.java).getTopHeadlinesByCategory(category, apiKey)

    override suspend fun getQuerySearchResult(
        query: String,
        apiKey: String,
        lang: String,
        page: Int
    ) = retrofit.create(ArticleService::class.java).getQuerySearchResult(query, apiKey, lang, page)
}