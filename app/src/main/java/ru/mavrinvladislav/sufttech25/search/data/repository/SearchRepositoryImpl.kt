package ru.mavrinvladislav.sufttech25.search.data.repository

import android.util.Log
import ru.mavrinvladislav.sufttech25.common.util.Either
import ru.mavrinvladislav.sufttech25.common.util.RemoteConstants
import ru.mavrinvladislav.sufttech25.search.data.mapper.toEntity
import ru.mavrinvladislav.sufttech25.search.data.remote.SearchService
import ru.mavrinvladislav.sufttech25.search.domain.model.Book
import ru.mavrinvladislav.sufttech25.search.domain.repository.SearchRepository
import javax.inject.Inject

class SearchRepositoryImpl @Inject constructor(
    private val searchService: SearchService
) : SearchRepository {

    private var page = START_PAGE
    private var lastQuery = EMPTY_QUERY

    override suspend fun fetchBooks(query: String): Either<List<Book>, String> {
        try {
            if (lastQuery != query) {
                resetPagination()
            }
            lastQuery = query

            val response = searchService.fetchSearch(query, page)

            if (!response.isSuccessful || response.body() == null) {
                return Either.Failure(response.message())
            }
            Log.d(LOG_TAG, response.body()!!.books.toString())
            val books = response.body()!!.books.map { it.toEntity() }
            return if (books.isEmpty()) {
                Either.Failure("No books found")
            } else {
                Either.Success(books)
            }
        } catch (e: Exception) {
            return Either.Failure(e.message ?: RemoteConstants.UNKNOWN_ERROR)
        }
    }

    private fun resetPagination() {
        page = START_PAGE
    }

    companion object {
        private const val LOG_TAG = "SearchRepositoryImpl"
        private const val START_PAGE = 1
        private const val EMPTY_QUERY = ""
    }
}