package com.edrees.newsapp.model

import android.os.Parcelable
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import kotlinx.parcelize.Parcelize

@Parcelize
data class Category(
    @StringRes val nameRes: Int,
    @DrawableRes val imageRes: Int
): Parcelable
