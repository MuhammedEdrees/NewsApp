package com.edrees.newsapp.network

import android.util.Log
import com.edrees.newsapp.model.ArticleResponse

object APIClient: ArticleRemoteDataSource {
    override suspend fun getTopHeadlinesByCountry(
        country: String,
        apiKey: String
    ) = BaseRetrofitHelper.retrofit.create(ArticleService::class.java).getTopHeadlinesByCountry(country, apiKey)
}