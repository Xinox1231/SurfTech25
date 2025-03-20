package ru.mavrinvladislav.sufttech25.details.presentation

import com.arkivanov.mvikotlin.core.store.Reducer
import com.arkivanov.mvikotlin.core.store.Store
import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.arkivanov.mvikotlin.extensions.coroutines.CoroutineBootstrapper
import com.arkivanov.mvikotlin.extensions.coroutines.CoroutineExecutor
import kotlinx.coroutines.launch
import ru.mavrinvladislav.sufttech25.common.domain.model.Book
import ru.mavrinvladislav.sufttech25.common.domain.use_case.ChangeFavouriteStatusUseCase
import ru.mavrinvladislav.sufttech25.details.presentation.DetailsStore.Intent
import ru.mavrinvladislav.sufttech25.details.presentation.DetailsStore.Label
import ru.mavrinvladislav.sufttech25.details.presentation.DetailsStore.State
import ru.mavrinvladislav.sufttech25.favourite.domain.use_case.ObserveIsBookFavouriteUseCase
import javax.inject.Inject

interface DetailsStore : Store<Intent, State, Label> {

    sealed interface Intent {

        data object OnBackButtonClicked : Intent

        data class ChangeFavouriteBookState(val book: Book) : Intent
    }

    data class State(
        val book: Book,
    )

    sealed interface Label {
        data object OnBackButtonClicked : Label
    }
}

class DetailsStoreFactory @Inject constructor(
    private val storeFactory: StoreFactory,
    private val observeIsBookFavouriteUseCase: ObserveIsBookFavouriteUseCase,
    private val changeFavouriteStatusUseCase: ChangeFavouriteStatusUseCase
) {

    fun create(
        book: Book
    ): DetailsStore =
        object : DetailsStore, Store<Intent, State, Label> by storeFactory.create(
            name = "DetailsStore",
            initialState = State(
                book = book
            ),
            bootstrapper = BootstrapperImpl(book.id),
            executorFactory = ::ExecutorImpl,
            reducer = ReducerImpl
        ) {}

    private sealed interface Action {
        data class FavouriteStateChanged(
            val isFavourite: Boolean
        ) : Action
    }

    private sealed interface Msg {
        data class FavouriteStateChanged(
            val isFavourite: Boolean
        ) : Msg
    }

    private inner class BootstrapperImpl(val bookId: String) : CoroutineBootstrapper<Action>() {
        override fun invoke() {
            scope.launch {
                observeIsBookFavouriteUseCase(bookId).collect {
                    dispatch(Action.FavouriteStateChanged(it))
                }
            }
        }
    }

    private inner class ExecutorImpl : CoroutineExecutor<Intent, Action, State, Msg, Label>() {
        override fun executeIntent(intent: Intent, getState: () -> State) {
            when (intent) {
                is Intent.ChangeFavouriteBookState -> {
                    scope.launch {
                        val book = getState().book
                        if (book.isFavourite) {
                            changeFavouriteStatusUseCase.remove(book.id)
                        } else {
                            changeFavouriteStatusUseCase.add(book)
                        }
                    }
                }

                is Intent.OnBackButtonClicked -> {
                    publish(Label.OnBackButtonClicked)
                }

            }
        }

        override fun executeAction(action: Action, getState: () -> State) {
            when (action) {
                is Action.FavouriteStateChanged -> {
                    dispatch(Msg.FavouriteStateChanged(action.isFavourite))
                }
            }
        }
    }

    private object ReducerImpl : Reducer<State, Msg> {
        override fun State.reduce(msg: Msg): State =
            when (msg) {
                is Msg.FavouriteStateChanged -> {
                    copy(book = book.copy(isFavourite = msg.isFavourite))
                }

            }
    }
}
