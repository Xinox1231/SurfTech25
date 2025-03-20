package ru.mavrinvladislav.sufttech25.favourite.domain.repository

import kotlinx.coroutines.flow.Flow
import ru.mavrinvladislav.sufttech25.common.domain.model.Book

interface FavouriteRepository {

    fun getFavouriteBooksId(): Flow<Set<String>>

    fun getFavouriteBooks(): Flow<List<Book>>

    suspend fun addBookToFavourite(book: Book)

    suspend fun deleteBookFromFavourite(bookId: String)
}