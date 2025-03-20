package ru.mavrinvladislav.sufttech25.details.presentation

import com.arkivanov.decompose.ComponentContext
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject

class DefaultDetailsComponent @AssistedInject constructor(
    @Assisted("componentContext")
    componentContext: ComponentContext
) : DetailsComponent {

    @AssistedFactory
    interface Factory {
        fun create(
            @Assisted("componentContext")
            componentContext: ComponentContext
        ): DefaultDetailsComponent
    }
}