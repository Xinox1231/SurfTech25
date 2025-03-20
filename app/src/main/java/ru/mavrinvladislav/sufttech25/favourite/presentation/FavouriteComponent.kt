package ru.mavrinvladislav.sufttech25.favourite.presentation

import kotlinx.coroutines.flow.StateFlow
import ru.mavrinvladislav.sufttech25.common.domain.model.Book

interface FavouriteComponent {

    val model: StateFlow<FavouriteStore.State>

    fun onBackButtonClick()

    fun onBookClick(book: Book)

    fun onChangeBookFavouriteStateClick(book: Book)
}