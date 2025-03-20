package ru.mavrinvladislav.sufttech25.search.domain.use_case

import ru.mavrinvladislav.sufttech25.search.domain.repository.SearchRepository
import javax.inject.Inject

class FetchBooksUseCase @Inject constructor(
    private val repository: SearchRepository
) {
    suspend operator fun invoke(query: String) = repository.fetchBooks(query)
}