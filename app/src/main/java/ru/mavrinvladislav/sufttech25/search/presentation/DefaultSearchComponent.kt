package ru.mavrinvladislav.sufttech25.search.presentation

import com.arkivanov.decompose.ComponentContext
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject


class DefaultSearchComponent @AssistedInject constructor(
    @Assisted("componentContext")
    componentContext: ComponentContext
) : SearchComponent {

    @AssistedFactory
    interface Factory {

        fun create(
            @Assisted("componentContext")
            componentContext: ComponentContext
        ): DefaultSearchComponent
    }
}