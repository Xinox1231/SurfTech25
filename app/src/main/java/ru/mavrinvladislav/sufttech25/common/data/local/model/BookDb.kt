package ru.mavrinvladislav.sufttech25.common.data.local.model

import androidx.room.Entity
import androidx.room.PrimaryKey

    @Entity(tableName = "favourite_books")
data class BookDb(
    @PrimaryKey
    val id: String,
    val title: String,
    val authors: List<String>,
    val publishedDate: String,
    val img: String,
    val description: String
)