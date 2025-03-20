package ru.mavrinvladislav.sufttech25.details.presentation

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

class DefaultDetailsComponent @AssistedInject constructor(
    private val detailsStoreFactory: DetailsStoreFactory,
    @Assisted("book")
    private val book: Book,
    @Assisted("onBackButtonClicked")
    onBackButtonClicked: () -> Unit,
    @Assisted("componentContext")
    componentContext: ComponentContext
) : DetailsComponent, ComponentContext by componentContext {

    private val store = instanceKeeper.getStore {
        detailsStoreFactory.create(book)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    override val model: StateFlow<DetailsStore.State>
        get() = store.stateFlow

    override fun onBackButtonClick() {
        store.accept(DetailsStore.Intent.OnBackButtonClicked)
    }

    private val scope = componentScope()

    init {
        scope.launch {
            store.labels.collect {
                when (it) {
                    is DetailsStore.Label.OnBackButtonClicked -> {
                        onBackButtonClicked()
                    }
                }
            }
        }
    }

    override fun changeFavouriteBookState(book: Book) {
        store.accept(DetailsStore.Intent.ChangeFavouriteBookState(book))
    }


    @AssistedFactory
    interface Factory {
        fun create(
            @Assisted("book")
            book: Book,
            @Assisted("onBackButtonClicked")
            onBackButtonClicked: () -> Unit,
            @Assisted("componentContext")
            componentContext: ComponentContext
        ): DefaultDetailsComponent
    }
}