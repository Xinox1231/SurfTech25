package ru.mavrinvladislav.sufttech25.common.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class Book(
    val id: String,
    val title: String,
    val authors: List<String>,
    val publishedDate: String,
    val img: String,
    val description: String,
    val isFavourite: Boolean = false
)