package com.edrees.newsapp.repo

import com.edrees.newsapp.local.LocalSource
import com.edrees.newsapp.model.Article
import com.edrees.newsapp.model.ArticleResponse
import com.edrees.newsapp.network.ArticleRemoteDataSource
import com.edrees.newsapp.util.Constants.country

class ArticleRepositoryImpl(
    private val remoteSource: ArticleRemoteDataSource,
    private val localSource: LocalSource
    ) : ArticleRepository {
    override suspend fun getTopHeadlines(language: String, country: String, apiKey: String): ArticleResponse {
        return remoteSource.getTopHeadlines(language, country, apiKey)
    }

    override suspend fun getTopHeadlinesByCategory(
        category: String,
        apiKey: String
    ): ArticleResponse {
        return remoteSource.getTopHeadlinesByCategory(category, apiKey)
    }

    override suspend fun getQuerySearchResult(
        query: String,
        apiKey: String,
        lang: String,
        page: Int
    ): ArticleResponse {
        return remoteSource.getQuerySearchResult(query, apiKey, lang, page)
    }

    override suspend fun insertLocalArticle(vararg articles: Article) {
        localSource.insertArticle(*articles)
    }

    override suspend fun getLocalArticles() = localSource.getAllArticles()
    override suspend fun deleteLocalArticle(article: Article) = localSource.deleteArticle(article)
    override suspend fun checkIfExistsLocally(article: Article) = localSource.checkIfExists(article.url)

}