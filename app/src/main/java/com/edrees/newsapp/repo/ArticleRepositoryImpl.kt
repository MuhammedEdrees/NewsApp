package com.edrees.newsapp.repo

import com.edrees.newsapp.local.LocalSource
import com.edrees.newsapp.model.Article
import com.edrees.newsapp.model.ArticleResponse
import com.edrees.newsapp.network.ArticleRemoteDataSource

class ArticleRepositoryImpl(
    private val remoteSource: ArticleRemoteDataSource,
    private val localSource: LocalSource
    ) : ArticleRepository {
    override suspend fun getTopHeadlinesByCountry(country: String, apiKey: String): ArticleResponse {
        return remoteSource.getTopHeadlinesByCountry(country, apiKey)
    }

    override suspend fun getTopHeadlinesByCategory(
        category: String,
        apiKey: String
    ): ArticleResponse {
        return remoteSource.getTopHeadlinesByCategory(category, apiKey)
    }

    override suspend fun insertArticle(vararg articles: Article) {
        localSource.insertArticle(*articles)
    }

}