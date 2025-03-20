package ru.mavrinvladislav.sufttech25.favourite.presentation

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.mvikotlin.core.instancekeeper.getStore
import com.arkivanov.mvikotlin.extensions.coroutines.labels
import com.arkivanov.mvikotlin.extensions.coroutines.stateFlow
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import ru.mavrinvladislav.sufttech25.common.domain.model.Book
import ru.mavrinvladislav.sufttech25.common.util.componentScope

class DefaultFavouriteComponent @AssistedInject constructor(
    private val favouriteStoreFactory: FavouriteStoreFactory,
    @Assisted("onBackButtonClicked")
    onBackButtonClicked: () -> Unit,
    @Assisted("onBookClicked")
    onBookClicked: (Book) -> Unit,
    @Assisted("componentContext")
    componentContext: ComponentContext
) : FavouriteComponent, ComponentContext by componentContext {

    private val store = instanceKeeper.getStore { favouriteStoreFactory.create() }
    private val scope = componentScope()

    init {
        scope.launch {
            store.labels.collect {
                when (it) {
                    is FavouriteStore.Label.OnBackButtonClicked -> {
                        onBackButtonClicked()
                    }

                    is FavouriteStore.Label.OnBookClicked -> {
                        onBookClicked(it.book)
                    }
                }
            }
        }
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    override val model: StateFlow<FavouriteStore.State>
        get() = store.stateFlow

    override fun onBackButtonClick() {
        store.accept(FavouriteStore.Intent.OnBackButtonClicked)
    }

    override fun onBookClick(book: Book) {
        store.accept(FavouriteStore.Intent.OnBookClicked(book))
    }

    override fun onChangeBookFavouriteStateClick(book: Book) {
        store.accept(FavouriteStore.Intent.ChangeFavouriteBookState(book))
    }

    @AssistedFactory
    interface Factory {

        fun create(
            @Assisted("onBackButtonClicked")
            onBackButtonClicked: () -> Unit,
            @Assisted("onBookClicked")
            onBookClicked: (Book) -> Unit,
            @Assisted("componentContext")
            componentContext: ComponentContext
        ): DefaultFavouriteComponent
    }
}