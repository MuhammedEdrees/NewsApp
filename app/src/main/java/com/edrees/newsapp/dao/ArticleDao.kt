package com.edrees.newsapp.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.edrees.newsapp.model.Article

@Dao
interface ArticleDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertArticle(vararg articles: Article)
    @Query("select * from articles")
    suspend fun getAllArticles(): List<Article>
}