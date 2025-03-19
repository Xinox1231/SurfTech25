package ru.mavrinvladislav.sufttech25.main.presentation

import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.value.Value
import ru.mavrinvladislav.sufttech25.favourite.presentation.FavouriteComponent
import ru.mavrinvladislav.sufttech25.search.presentation.SearchComponent

interface MainComponent {

    val stackNavigation: Value<ChildStack<*, MainChild>>

    fun onTabSelected(tab: MainTab)
}

sealed interface MainChild {

    data class Search(val component: SearchComponent) : MainChild

    data class Favourite(val component: FavouriteComponent) : MainChild
}