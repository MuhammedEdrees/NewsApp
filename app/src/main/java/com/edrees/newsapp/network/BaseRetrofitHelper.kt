package com.edrees.newsapp.network

//import com.google.gson.GsonBuilder
//import retrofit2.Retrofit
//import retrofit2.converter.gson.GsonConverterFactory
//
//object BaseRetrofitHelper {
//    private val gson = GsonBuilder().serializeNulls().create()
//    private val url = "https://newsapi.org/v2/"
//    val retrofit = Retrofit.Builder()
//        .baseUrl(url)
//        .addConverterFactory(GsonConverterFactory.create(gson))
//        .build()
//}
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

object BaseRetrofitHelper {
    private val moshi = Moshi.Builder()
        .addLast(KotlinJsonAdapterFactory()) // Add Kotlin support
        .build()

    private val url = "https://newsapi.org/v2/"
    val retrofit = Retrofit.Builder()
        .baseUrl(url)
        .addConverterFactory(MoshiConverterFactory.create(moshi)) // Use MoshiConverterFactory
        .build()
}
