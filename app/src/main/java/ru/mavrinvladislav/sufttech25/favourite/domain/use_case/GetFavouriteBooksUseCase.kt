package ru.mavrinvladislav.sufttech25.favourite.domain.use_case

import ru.mavrinvladislav.sufttech25.favourite.domain.repository.FavouriteRepository
import javax.inject.Inject

class GetFavouriteBooksUseCase @Inject constructor(
    private val repository: FavouriteRepository
) {
    operator fun invoke() = repository.getFavouriteBooks()
}