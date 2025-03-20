package ru.mavrinvladislav.sufttech25.favourite.presentation

import com.arkivanov.mvikotlin.core.store.Reducer
import com.arkivanov.mvikotlin.core.store.Store
import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.arkivanov.mvikotlin.extensions.coroutines.CoroutineBootstrapper
import com.arkivanov.mvikotlin.extensions.coroutines.CoroutineExecutor
import kotlinx.coroutines.launch
import ru.mavrinvladislav.sufttech25.common.domain.model.Book
import ru.mavrinvladislav.sufttech25.common.domain.use_case.ChangeFavouriteStatusUseCase
import ru.mavrinvladislav.sufttech25.favourite.domain.use_case.GetFavouriteBooksUseCase
import ru.mavrinvladislav.sufttech25.favourite.presentation.FavouriteStore.Intent
import ru.mavrinvladislav.sufttech25.favourite.presentation.FavouriteStore.Label
import ru.mavrinvladislav.sufttech25.favourite.presentation.FavouriteStore.State
import javax.inject.Inject

interface FavouriteStore : Store<Intent, State, Label> {

    sealed interface Intent {

        data object OnBackButtonClicked : Intent

        data class OnBookClicked(val book: Book) : Intent

        data class ChangeFavouriteBookState(val book: Book) : Intent
    }

    data class State(
        val favouriteBooks: List<Book>
    )

    sealed interface Label {
        data object OnBackButtonClicked : Label

        data class OnBookClicked(val book: Book) : Label
    }
}

class FavouriteStoreFactory @Inject constructor(
    private val storeFactory: StoreFactory,
    private val getFavouriteBooksUseCase: GetFavouriteBooksUseCase,
    private val changeFavouriteStatusUseCase: ChangeFavouriteStatusUseCase
) {

    fun create(): FavouriteStore =
        object : FavouriteStore, Store<Intent, State, Label> by storeFactory.create(
            name = "FavouriteStore",
            initialState = State(
                favouriteBooks = emptyList()
            ),
            bootstrapper = BootstrapperImpl(),
            executorFactory = ::ExecutorImpl,
            reducer = ReducerImpl
        ) {}

    private sealed interface Action {
        data class FavouriteBooksLoaded(val bookList: List<Book>) : Action
    }

    private sealed interface Msg {
        data class FavouriteBooksLoaded(val bookList: List<Book>) : Msg

    }

    private inner class BootstrapperImpl : CoroutineBootstrapper<Action>() {
        override fun invoke() {
            scope.launch {
                getFavouriteBooksUseCase().collect {
                    dispatch(Action.FavouriteBooksLoaded(it))
                }
            }
        }
    }

    private inner class ExecutorImpl : CoroutineExecutor<Intent, Action, State, Msg, Label>() {
        override fun executeIntent(intent: Intent, getState: () -> State) {
            when (intent) {
                is Intent.ChangeFavouriteBookState -> {
                    scope.launch {
                        val book = intent.book
                        changeFavouriteStatusUseCase.remove(book.id)
                    }
                }

                is Intent.OnBackButtonClicked -> {
                    publish(Label.OnBackButtonClicked)
                }

                is Intent.OnBookClicked -> {
                    publish(Label.OnBookClicked(intent.book))
                }
            }
        }

        override fun executeAction(action: Action, getState: () -> State) {
            when (action) {
                is Action.FavouriteBooksLoaded -> {
                    dispatch(Msg.FavouriteBooksLoaded(action.bookList))
                }
            }
        }
    }

    private object ReducerImpl : Reducer<State, Msg> {
        override fun State.reduce(msg: Msg): State =
            when (msg) {
                is Msg.FavouriteBooksLoaded -> {
                    copy(
                        favouriteBooks = msg.bookList
                    )
                }
            }
    }
}
