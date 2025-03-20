package ru.mavrinvladislav.sufttech25.root.presentation

import androidx.compose.runtime.Composable
import com.arkivanov.decompose.extensions.compose.jetpack.stack.Children
import ru.mavrinvladislav.sufttech25.common.ui.theme.SuftTech25Theme
import ru.mavrinvladislav.sufttech25.details.presentation.DetailsContent
import ru.mavrinvladislav.sufttech25.main.presentation.MainContent

@Composable
fun RootContent(component: RootComponent) {

    SuftTech25Theme(
        dynamicColor = false
    ) {
        Children(
            stack = component.stackNavigation
        ) {
            when (val instance = it.instance) {
                is RootChild.Main -> {
                    MainContent(instance.component)
                }

                is RootChild.Details -> {
                    DetailsContent(instance.component)
                }
            }
        }
    }
}