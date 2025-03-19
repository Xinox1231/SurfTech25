package ru.mavrinvladislav.sufttech25.search.data.mapper

import ru.mavrinvladislav.sufttech25.common.util.StringConstants
import ru.mavrinvladislav.sufttech25.common.util.toFormattedDate
import ru.mavrinvladislav.sufttech25.search.data.remote.model.BookDto
import ru.mavrinvladislav.sufttech25.search.domain.model.Book

fun BookDto.toEntity() = Book(
    id = id,
    title = volumeInfo.title ?: StringConstants.UNKNOWN,
    authors = volumeInfo.authors ?: listOf(StringConstants.UNKNOWN),
    publishedDate = volumeInfo.publishedDate.toFormattedDate(),
    img = volumeInfo.imageLinks?.image ?: StringConstants.UNKNOWN,
    description = volumeInfo.description ?: StringConstants.UNKNOWN
)