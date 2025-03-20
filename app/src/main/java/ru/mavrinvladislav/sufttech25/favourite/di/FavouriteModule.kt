package ru.mavrinvladislav.sufttech25.favourite.di

import dagger.Binds
import dagger.Module
import ru.mavrinvladislav.sufttech25.favourite.data.repository.FavouriteRepositoryImpl
import ru.mavrinvladislav.sufttech25.favourite.domain.repository.FavouriteRepository

@Module
interface FavouriteModule {

    @Binds
    fun bindFavouriteRepository(impl: FavouriteRepositoryImpl): FavouriteRepository

}