package ru.mavrinvladislav.sufttech25.search.presentation

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


class DefaultSearchComponent @AssistedInject constructor(
    private val searchStoreFactory: SearchStoreFactory,
    @Assisted("onBookClicked")
    onBookClicked: (Book) -> Unit,
    @Assisted("componentContext")
    componentContext: ComponentContext
) : SearchComponent, ComponentContext by componentContext {

    private val store = instanceKeeper.getStore { searchStoreFactory.create() }
    private val scope = componentScope()

    init {
        scope.launch {
            store.labels.collect {
                when (it) {
                    is SearchStore.Label.ClickedOnBook -> {
                        onBookClicked(it.book)
                    }
                }
            }
        }
    }

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

    override fun clearQuery() {
        store.accept(SearchStore.Intent.ClearQuery)
    }


    @AssistedFactory
    interface Factory {
        fun create(
            @Assisted("onBookClicked")
            onBookClicked: (Book) -> Unit,
            @Assisted("componentContext")
            componentContext: ComponentContext
        ): DefaultSearchComponent
    }
}