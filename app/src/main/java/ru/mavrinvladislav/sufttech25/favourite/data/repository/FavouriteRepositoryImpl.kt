package ru.mavrinvladislav.sufttech25.favourite.data.repository

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import ru.mavrinvladislav.sufttech25.common.data.local.FavouriteBooksDao
import ru.mavrinvladislav.sufttech25.favourite.domain.repository.FavouriteRepository
import ru.mavrinvladislav.sufttech25.common.data.mapper.toDb
import ru.mavrinvladislav.sufttech25.common.data.mapper.toEntity
import ru.mavrinvladislav.sufttech25.common.domain.model.Book
import javax.inject.Inject

class FavouriteRepositoryImpl @Inject constructor(
    private val favouriteBooksDao: FavouriteBooksDao
) : FavouriteRepository {
    override fun getFavouriteBooksId(): Flow<Set<String>> =
        favouriteBooksDao.getFavouriteBooksId().map {
            it.toSet()
        }

    override fun getFavouriteBooks(): Flow<List<Book>> =
        favouriteBooksDao.getFavouriteBooks().map { list ->
            list.map {
                it.toEntity()
            }
        }

    override fun observeIsBookFavourite(bookId: String): Flow<Boolean> =
        favouriteBooksDao.observeIsFavourite(bookId)


    override suspend fun addBookToFavourite(book: Book) {
        favouriteBooksDao.addBookToFavourite(book.toDb())
    }

    override suspend fun deleteBookFromFavourite(bookId: String) {
        favouriteBooksDao.deleteFavouriteBook(bookId)
    }


}