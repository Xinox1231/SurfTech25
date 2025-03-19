package ru.mavrinvladislav.sufttech25.favourite.presentation

import com.arkivanov.decompose.ComponentContext
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject

class DefaultFavouriteComponent @AssistedInject constructor(
    @Assisted("componentContext")
    componentContext: ComponentContext
) : FavouriteComponent{

    @AssistedFactory
    interface Factory {

        fun create(
            @Assisted("componentContext")
            componentContext: ComponentContext
        ): DefaultFavouriteComponent
    }
}