package ru.mavrinvladislav.sufttech25.root.presentation

import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.value.Value
import ru.mavrinvladislav.sufttech25.details.presentation.DetailsComponent
import ru.mavrinvladislav.sufttech25.main.presentation.MainComponent

interface RootComponent {

    val stackNavigation: Value<ChildStack<*, RootChild>>
}

sealed interface RootChild {

    data class Main(val component: MainComponent) : RootChild

    data class Details(val component: DetailsComponent) : RootChild
}