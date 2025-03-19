package ru.mavrinvladislav.sufttech25.search.data.remote.model

import com.google.gson.annotations.SerializedName

data class ImageDto(
    @SerializedName("thumbnail")
    val image: String
)
