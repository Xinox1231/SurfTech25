package ru.mavrinvladislav.sufttech25.search.data

import ru.mavrinvladislav.sufttech25.common.data.mapper.toEntity
import ru.mavrinvladislav.sufttech25.common.domain.model.Book
import ru.mavrinvladislav.sufttech25.common.util.Either
import ru.mavrinvladislav.sufttech25.common.util.RemoteConstants
import ru.mavrinvladislav.sufttech25.search.data.remote.SearchService
import ru.mavrinvladislav.sufttech25.search.domain.repository.SearchRepository
import javax.inject.Inject

class SearchRepositoryImpl @Inject constructor(
    private val searchService: SearchService
) : SearchRepository {

    override suspend fun fetchBooks(query: String): Either<List<Book>, String> {
        try {
            val newQuery = "${QUERY_PREFIX}${query}"
            val response = searchService.fetchSearch(newQuery)
            if (!response.isSuccessful || response.body() == null) {
                return Either.Failure(response.message())
            }
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

    companion object {
        private const val LOG_TAG = "SearchRepositoryImpl"
        private const val QUERY_PREFIX = "intitle:"
    }
}