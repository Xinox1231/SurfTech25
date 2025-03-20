package ru.mavrinvladislav.sufttech25.main.presentation

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.router.stack.StackNavigation
import com.arkivanov.decompose.router.stack.bringToFront
import com.arkivanov.decompose.router.stack.childStack
import com.arkivanov.decompose.router.stack.pop
import com.arkivanov.decompose.value.Value
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import kotlinx.serialization.Serializable
import ru.mavrinvladislav.sufttech25.common.domain.model.Book
import ru.mavrinvladislav.sufttech25.favourite.presentation.DefaultFavouriteComponent
import ru.mavrinvladislav.sufttech25.search.presentation.DefaultSearchComponent

class DefaultMainComponent @AssistedInject constructor(
    private val searchComponentFactory: DefaultSearchComponent.Factory,
    private val favouriteComponentFactory: DefaultFavouriteComponent.Factory,
    @Assisted("onBookClicked")
    private val onBookClicked: (Book) -> Unit,
    @Assisted("componentContext")
    componentContext: ComponentContext
) : MainComponent, ComponentContext by componentContext {

    private val navigation = StackNavigation<MainConfig>()

    override val stackNavigation: Value<ChildStack<*, MainChild>> = childStack(
        source = navigation,
        serializer = MainConfig.serializer(),
        handleBackButton = true,
        initialConfiguration = MainConfig.Search,
        childFactory = ::child
    )

    override fun onTabSelected(tab: MainTab) {
        val configuration = tab.toConfiguration()
        navigation.bringToFront(configuration)
    }

    fun child(config: MainConfig, componentContext: ComponentContext): MainChild {
        return when (config) {

            is MainConfig.Search -> {
                val component = searchComponentFactory.create(
                    onBookClicked = {
                        onBookClicked(it)
                    },
                    componentContext = componentContext
                )
                MainChild.Search(component)
            }

            is MainConfig.Favourite -> {
                val component = favouriteComponentFactory.create(
                    onBackButtonClicked = {
                        navigation.pop()
                    },
                    onBookClicked = {
                        onBookClicked(it)
                    },
                    componentContext = componentContext
                )
                MainChild.Favourite(component)
            }

        }
    }

    @Serializable
    sealed interface MainConfig {

        @Serializable
        data object Search : MainConfig

        @Serializable
        data object Favourite : MainConfig
    }

    @AssistedFactory
    interface Factory {
        fun create(
            @Assisted("onBookClicked")
            onBookClicked: (Book) -> Unit,
            @Assisted("componentContext")
            componentContext: ComponentContext
        ): DefaultMainComponent
    }
}