package ru.mavrinvladislav.sufttech25.search.data.remote.model

import com.google.gson.annotations.SerializedName

data class VolumeInfoDto(
    @SerializedName("title")
    val title: String?,
    @SerializedName("authors")
    val authors: List<String>?,
    @SerializedName("publishedDate")
    val publishedDate: String?,
    @SerializedName("description")
    val description: String?,
    @SerializedName("imageLinks")
    val imageLinks: ImageDto?
)
