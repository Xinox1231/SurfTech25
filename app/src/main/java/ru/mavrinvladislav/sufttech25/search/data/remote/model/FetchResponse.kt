package ru.mavrinvladislav.sufttech25.search.data.remote.model

import com.google.gson.annotations.SerializedName

data class FetchResponse(
    @SerializedName("items")
    val books: List<BookDto>
)