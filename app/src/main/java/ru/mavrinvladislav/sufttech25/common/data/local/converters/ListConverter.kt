package ru.mavrinvladislav.sufttech25.common.data.local.converters

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class ListConverter {

    // Преобразование List<String> -> String (JSON)
    @TypeConverter
    fun fromAuthorsList(authors: List<String>): String {
        return Gson().toJson(authors)
    }

    // Преобразование String (JSON) -> List<String>
    @TypeConverter
    fun toAuthorsList(authorsString: String): List<String> {
        val type = object : TypeToken<List<String>>() {}.type
        return Gson().fromJson(authorsString, type)
    }
}