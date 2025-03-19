package ru.mavrinvladislav.sufttech25.root.presentation

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.router.stack.StackNavigation
import com.arkivanov.decompose.router.stack.childStack
import com.arkivanov.decompose.value.Value
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import kotlinx.serialization.Serializable
import ru.mavrinvladislav.sufttech25.main.presentation.DefaultMainComponent

class DefaultRootComponent @AssistedInject constructor(
    private val mainComponentFactory: DefaultMainComponent.Factory,
    @Assisted("componentContext")
    componentContext: ComponentContext
) : RootComponent, ComponentContext by componentContext {

    private val navigation = StackNavigation<RootConfig>()

    override val stackNavigation: Value<ChildStack<*, RootChild>> = childStack(
        source = navigation,
        initialConfiguration = RootConfig.Main,
        serializer = RootConfig.serializer(),
        childFactory = ::child
    )

    fun child(config: RootConfig, componentContext: ComponentContext): RootChild {
        return when (config) {
            is RootConfig.Main -> {
                val component = mainComponentFactory.create(componentContext)
                RootChild.Main(component)
            }
        }
    }

    @Serializable
    sealed interface RootConfig {

        @Serializable
        data object Main : RootConfig
    }

    @AssistedFactory
    interface Factory {

        fun create(
            @Assisted("componentContext")
            componentContext: ComponentContext
        ): DefaultRootComponent
    }
}