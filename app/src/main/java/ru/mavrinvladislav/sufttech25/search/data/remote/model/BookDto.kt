package ru.mavrinvladislav.sufttech25.search.data.remote.model

import com.google.gson.annotations.SerializedName

data class BookDto(
    @SerializedName("id")
    val id: String,
    @SerializedName("volumeInfo")
    val volumeInfo: VolumeInfoDto
)
