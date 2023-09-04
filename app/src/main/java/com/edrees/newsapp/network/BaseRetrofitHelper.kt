package com.edrees.newsapp.network

import com.google.gson.GsonBuilder
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object BaseRetrofitHelper {
    private val gson = GsonBuilder().serializeNulls().create()
    private val url = "https://newsapi.org/v2/"
    val retrofit = Retrofit.Builder()
        .baseUrl(url)
        .addConverterFactory(GsonConverterFactory.create(gson))
        .build()
}