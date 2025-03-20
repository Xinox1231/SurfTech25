package ru.mavrinvladislav.sufttech25.common.domain.use_case

import ru.mavrinvladislav.sufttech25.favourite.domain.repository.FavouriteRepository
import javax.inject.Inject

class GetFavouriteBooksIdUseCase @Inject constructor(
    private val repository: FavouriteRepository
) {
    operator fun invoke() = repository.getFavouriteBooksId()
}