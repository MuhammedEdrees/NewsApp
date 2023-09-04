package com.edrees.newsapp.db

import androidx.room.TypeConverter
import com.edrees.newsapp.model.Source

class Converters {
    @TypeConverter
    fun sourceToString(source: Source): String {
        return source.id+","+source.name
    }

    @TypeConverter
    fun stringToSource(str: String): Source {
        val param = str.split(",")
        return Source(param[0], param[1])
    }
}