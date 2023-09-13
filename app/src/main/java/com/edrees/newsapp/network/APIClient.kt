package com.edrees.newsapp.network

import android.util.Log
import com.edrees.newsapp.model.ArticleResponse
import retrofit2.create

object APIClient: ArticleRemoteDataSource {
    override suspend fun getTopHeadlines(
        language: String,
        country: String,
        apiKey: String
    ) = BaseRetrofitHelper.retrofit.create(ArticleService::class.java).getTopHeadlines(language, country, apiKey)

    override suspend fun getTopHeadlinesByCategory(
        category: String,
        apiKey: String
    ) = BaseRetrofitHelper.retrofit.create(ArticleService::class.java).getTopHeadlinesByCategory(category, apiKey)

    override suspend fun getQuerySearchResult(
        query: String,
        apiKey: String,
        lang: String,
        page: Int
    ) = BaseRetrofitHelper.retrofit.create(ArticleService::class.java).getQuerySearchResult(query, apiKey, lang, page)
}