package ru.mavrinvladislav.sufttech25.search.presentation

import kotlinx.coroutines.flow.StateFlow
import ru.mavrinvladislav.sufttech25.common.domain.model.Book

interface SearchComponent {

    val model: StateFlow<SearchStore.State>

    fun startSearching()

    fun updateSearchQuery(newQuery: String)

    fun clickOnBook(book: Book)

    fun changeFavouriteStatus(book: Book)
}