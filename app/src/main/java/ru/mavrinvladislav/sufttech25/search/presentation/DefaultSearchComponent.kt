package ru.mavrinvladislav.sufttech25.search.presentation

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.mvikotlin.core.instancekeeper.getStore
import com.arkivanov.mvikotlin.extensions.coroutines.stateFlow
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.StateFlow
import ru.mavrinvladislav.sufttech25.common.domain.model.Book


class DefaultSearchComponent @AssistedInject constructor(
    private val searchStoreFactory: SearchStoreFactory,
    @Assisted("componentContext")
    componentContext: ComponentContext
) : SearchComponent, ComponentContext by componentContext {

    private val store = instanceKeeper.getStore { searchStoreFactory.create() }

    @OptIn(ExperimentalCoroutinesApi::class)
    override val model: StateFlow<SearchStore.State>
        get() = store.stateFlow

    override fun startSearching() {
        store.accept(SearchStore.Intent.StartSearching)
    }

    override fun updateSearchQuery(newQuery: String) {
        store.accept(SearchStore.Intent.UpdateSearchQuery(newQuery))
    }

    override fun clickOnBook(book: Book) {
        store.accept(SearchStore.Intent.ClickOnBook(book))
    }

    override fun changeFavouriteStatus(book: Book) {
        store.accept(SearchStore.Intent.ChangeBookFavouriteStatus(book))
    }


    @AssistedFactory
    interface Factory {
        fun create(
            @Assisted("componentContext")
            componentContext: ComponentContext
        ): DefaultSearchComponent
    }
}