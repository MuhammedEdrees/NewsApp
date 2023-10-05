package com.edrees.newsapp.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.edrees.newsapp.dao.ArticleDao
import com.edrees.newsapp.model.Article

@Database(entities = [Article::class], version = 2)
@TypeConverters(Converters::class)
abstract class NewsDatabase : RoomDatabase(){
    abstract fun articleDao(): ArticleDao
}