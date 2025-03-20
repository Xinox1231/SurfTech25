package ru.mavrinvladislav.sufttech25.details.presentation

import kotlinx.coroutines.flow.StateFlow
import ru.mavrinvladislav.sufttech25.common.domain.model.Book

interface DetailsComponent {

    val model: StateFlow<DetailsStore.State>

    fun onBackButtonClick()

    fun changeFavouriteBookState(book: Book)
}