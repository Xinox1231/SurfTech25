package ru.mavrinvladislav.sufttech25.search.domain.model

data class Book(
    val id: String,
    val title: String,
    val authors: List<String>,
    val publishedDate: String,
    val img: String,
    val description: String
)