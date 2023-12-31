package com.edrees.newsapp.model

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize
import java.io.Serializable

@Entity("articles")
@Parcelize
data class Article(
    val author: String?,
    val content: String?,
    val description: String?,
    @ColumnInfo(name="puplished_at")val publishedAt: String,
    val source: Source?,
    val title: String?,
    @PrimaryKey val url: String,
    @ColumnInfo(name="url_to_image")val urlToImage: String?
): Parcelable