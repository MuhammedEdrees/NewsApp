package com.edrees.newsapp.model

data class ArticleResponse(
    val articles: List<Article>,
    val status: String,
    val totalResults: Int
)