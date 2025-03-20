package ru.mavrinvladislav.sufttech25.search.domain.repository

import ru.mavrinvladislav.sufttech25.common.util.Either
import ru.mavrinvladislav.sufttech25.common.domain.model.Book

interface SearchRepository {

    suspend fun fetchBooks(
        query: String,
    ): Either<List<Book>, String>
}