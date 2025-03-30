package ru.mavrinvladislav.sufttech25.search.presentation

import android.util.Log
import com.arkivanov.mvikotlin.core.store.Reducer
import com.arkivanov.mvikotlin.core.store.Store
import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.arkivanov.mvikotlin.extensions.coroutines.CoroutineBootstrapper
import com.arkivanov.mvikotlin.extensions.coroutines.CoroutineExecutor
import kotlinx.coroutines.launch
import ru.mavrinvladislav.sufttech25.common.domain.model.Book
import ru.mavrinvladislav.sufttech25.common.domain.use_case.ChangeFavouriteStatusUseCase
import ru.mavrinvladislav.sufttech25.common.domain.use_case.GetFavouriteBooksIdUseCase
import ru.mavrinvladislav.sufttech25.search.domain.use_case.FetchBooksUseCase
import ru.mavrinvladislav.sufttech25.search.presentation.SearchStore.Intent
import ru.mavrinvladislav.sufttech25.search.presentation.SearchStore.Label
import ru.mavrinvladislav.sufttech25.search.presentation.SearchStore.State
import ru.mavrinvladislav.sufttech25.search.presentation.SearchStore.State.SearchState
import javax.inject.Inject

interface SearchStore : Store<Intent, State, Label> {

    sealed interface Intent {

        data class UpdateSearchQuery(val newQuery: String) : Intent

        data object StartSearching : Intent

        data class ClickOnBook(val book: Book) : Intent

        data class ChangeBookFavouriteStatus(val book: Book) : Intent

        data object ClearQuery : Intent

    }

    data class State(
        val query: String,
        val searchState: SearchState,
        val favouriteIds: Set<String>
    ) {
        sealed interface SearchState {

            data object EmptyState : SearchState

            data object NotFound : SearchState

            data object Loading : SearchState

            data class BooksFound(val booksList: List<Book>) : SearchState

            data class Error(val message: String) : SearchState
        }
    }

    sealed interface Label {

        data class ClickedOnBook(val book: Book) : Label
    }
}

class SearchStoreFactory @Inject constructor(
    private val storeFactory: StoreFactory,
    private val fetchBooksUseCase: FetchBooksUseCase,
    private val getFavouriteBooksId: GetFavouriteBooksIdUseCase,
    private val changeFavouriteStatusUseCase: ChangeFavouriteStatusUseCase
) {

    fun create(): SearchStore =
        object : SearchStore, Store<Intent, State, Label> by storeFactory.create(
            name = "SearchStore",
            initialState = State(
                query = "",
                searchState = SearchState.EmptyState,
                favouriteIds = emptySet()
            ),
            bootstrapper = BootstrapperImpl(),
            executorFactory = ::ExecutorImpl,
            reducer = ReducerImpl
        ) {}

    private sealed interface Action {

        data class FavouriteBooksUpdated(val favouriteIds: Set<String>) : Action

    }

    private sealed interface Msg {

        data class SearchQueryUpdated(val newQuery: String) : Msg

        data object NotFound : Msg

        data object Loading : Msg

        data class BooksFound(val booksList: List<Book>, val favouriteIds: Set<String>) : Msg

        data class Error(val message: String) : Msg

        data class FavouriteBooksUpdated(val favouriteIds: Set<String>) : Msg

        data object QueryCleared : Msg

    }

    private inner class BootstrapperImpl : CoroutineBootstrapper<Action>() {
        override fun invoke() {
            scope.launch {
                getFavouriteBooksId().collect { favouriteIds ->
                    dispatch(Action.FavouriteBooksUpdated(favouriteIds))
                    Log.d(LOG_TAG, favouriteIds.toString())
                }
            }
        }
    }

    private inner class ExecutorImpl : CoroutineExecutor<Intent, Action, State, Msg, Label>() {
        override fun executeIntent(intent: Intent, getState: () -> State) {
            when (intent) {
                is Intent.ClickOnBook -> {
                    publish(Label.ClickedOnBook(intent.book))
                }

                is Intent.StartSearching -> {
                    scope.launch {
                        dispatch(Msg.Loading)
                        val query = getState().query
                        val favouriteIds = getState().favouriteIds
                        val result = fetchBooksUseCase(query)
                        result.fold(
                            onSuccess = { books ->
                                if (books.isEmpty()) {
                                    dispatch(Msg.NotFound)
                                } else {
                                    dispatch(Msg.BooksFound(books, favouriteIds))
                                }
                            },
                            onFailure = { message ->
                                dispatch(Msg.Error(message))
                            }
                        )
                    }
                }

                is Intent.UpdateSearchQuery -> {
                    dispatch(Msg.SearchQueryUpdated(intent.newQuery))
                }

                is Intent.ChangeBookFavouriteStatus -> {
                    scope.launch {
                        val book = intent.book
                        if (book.isFavourite) {
                            changeFavouriteStatusUseCase.remove(book.id)
                        } else {
                            changeFavouriteStatusUseCase.add(book) // Добавляем книгу в избранное
                        }
                    }
                }

                Intent.ClearQuery -> {
                    dispatch(Msg.QueryCleared)
                }
            }
        }

        override fun executeAction(action: Action, getState: () -> State) {
            when (action) {
                is Action.FavouriteBooksUpdated -> {
                    dispatch(Msg.FavouriteBooksUpdated(action.favouriteIds))
                }
            }
        }
    }

    private object ReducerImpl : Reducer<State, Msg> {
        override fun State.reduce(msg: Msg): State =
            when (msg) {
                is Msg.BooksFound -> {
                    val newBooksList = msg.booksList.map { book ->
                        book.copy(isFavourite = msg.favouriteIds.contains(book.id))
                    }
                    copy(
                        searchState = SearchState.BooksFound(newBooksList)
                    )
                }

                is Msg.Error -> {
                    copy(
                        searchState = SearchState.Error(msg.message)
                    )
                }

                is Msg.NotFound -> {
                    copy(
                        searchState = SearchState.NotFound
                    )
                }

                is Msg.Loading -> {
                    copy(
                        searchState = SearchState.Loading
                    )
                }

                is Msg.SearchQueryUpdated -> {
                    copy(
                        query = msg.newQuery
                    )
                }

                is Msg.FavouriteBooksUpdated -> {
                    copy(
                        favouriteIds = msg.favouriteIds,
                        searchState = when (val currentState = searchState) {
                            is SearchState.BooksFound -> {
                                currentState.copy(
                                    booksList = currentState.booksList.map { book ->
                                        book.copy(isFavourite = msg.favouriteIds.contains(book.id))
                                    }
                                )
                            }

                            else -> currentState
                        }
                    )
                }

                Msg.QueryCleared -> {
                    copy(
                        query = ""
                    )
                }
            }
    }

    companion object {
        private const val LOG_TAG = "SearchStore"
    }
}
