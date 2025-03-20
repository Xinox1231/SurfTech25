package ru.mavrinvladislav.sufttech25.common.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow
import ru.mavrinvladislav.sufttech25.common.data.local.model.BookDb

@Dao
interface FavouriteBooksDao {

    @Query("SELECT id from favourite_books")
    fun getFavouriteBooksId(): Flow<List<String>>

    @Query("SELECT * from favourite_books")
    fun getFavouriteBooks(): Flow<List<BookDb>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addBookToFavourite(book: BookDb)

    @Query("DELETE FROM favourite_books WHERE id =:bookId")
    suspend fun deleteFavouriteBook(bookId: String)

}