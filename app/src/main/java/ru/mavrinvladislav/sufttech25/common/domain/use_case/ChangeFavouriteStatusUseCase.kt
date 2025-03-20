package ru.mavrinvladislav.sufttech25.common.domain.use_case

import ru.mavrinvladislav.sufttech25.common.domain.model.Book
import ru.mavrinvladislav.sufttech25.favourite.domain.repository.FavouriteRepository
import javax.inject.Inject

class ChangeFavouriteStatusUseCase @Inject constructor(
    private val repository: FavouriteRepository
) {
    suspend fun add(book: Book) = repository.addBookToFavourite(book)

    suspend fun remove(bookId: String) = repository.deleteBookFromFavourite(bookId)
}