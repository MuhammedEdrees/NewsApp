package com.edrees.newsapp.di

import androidx.room.Room
import com.edrees.newsapp.dao.ArticleDao
import com.edrees.newsapp.db.NewsDatabase
import com.edrees.newsapp.local.LocalSource
import com.edrees.newsapp.local.LocalSourceImpl
import com.edrees.newsapp.network.ArticleRemoteDataSource
import com.edrees.newsapp.network.ArticleRemoteDataSourceImpl
import com.edrees.newsapp.repo.ArticleRepository
import com.edrees.newsapp.repo.ArticleRepositoryImpl
import com.edrees.newsapp.ui.bookmarks.BookmarksViewModel
import com.edrees.newsapp.ui.categories.categorized_news.CategorizedNewsViewModel
import com.edrees.newsapp.ui.details.DetailsViewModel
import com.edrees.newsapp.ui.home.HomeViewModel
import com.edrees.newsapp.ui.search.SearchViewModel
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import kotlin.reflect.KParameter

val localDataModule = module{
    single<NewsDatabase>{
        Room.databaseBuilder(androidContext(), NewsDatabase::class.java, "news_db")
            .fallbackToDestructiveMigration()
            .build()
    }
    single<ArticleDao>{
        get<NewsDatabase>().articleDao()
    }
    single<LocalSource>{
        LocalSourceImpl(get())
    }
}
val remoteDataModule = module{
    single<Retrofit>{
        val moshi = Moshi.Builder()
            .addLast(KotlinJsonAdapterFactory()) // Add Kotlin support
            .build()
        Retrofit.Builder()
            .baseUrl("https://newsapi.org/v2/")
            .addConverterFactory(MoshiConverterFactory.create(moshi)) // Use MoshiConverterFactory
            .build()
    }
    single<ArticleRemoteDataSource>{
        ArticleRemoteDataSourceImpl(get())
    }
}
val appModule = module{
    single<ArticleRepository>{
        ArticleRepositoryImpl(get(), get())
    }
    viewModel {
        DetailsViewModel(get())
    }
    viewModel {
        HomeViewModel(get())
    }
    viewModel {
        SearchViewModel(get())
    }
    viewModel {
        CategorizedNewsViewModel(get())
    }
    viewModel {
        BookmarksViewModel(get())
    }
}