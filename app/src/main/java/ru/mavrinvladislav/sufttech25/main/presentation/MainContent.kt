package ru.mavrinvladislav.sufttech25.main.presentation

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.SizeTransform
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import com.arkivanov.decompose.extensions.compose.jetpack.subscribeAsState
import ru.mavrinvladislav.sufttech25.common.ui.theme.brandBlue
import ru.mavrinvladislav.sufttech25.common.ui.theme.lightGray
import ru.mavrinvladislav.sufttech25.favourite.presentation.FavouriteContent
import ru.mavrinvladislav.sufttech25.search.presentation.SearchContent

@Composable
fun MainContent(component: MainComponent) {

    val stack by component.stackNavigation.subscribeAsState()

    Scaffold(
        bottomBar = {
            BottomAppBar(
                containerColor = Color.White
            ) {
                val tabs = listOf(MainTab.Search, MainTab.Favourite)
                tabs.forEach { tab ->
                    NavigationBarItem(
                        colors = NavigationBarItemDefaults.colors(
                            selectedIconColor = brandBlue,
                            selectedTextColor = brandBlue,
                            unselectedIconColor = lightGray,
                            unselectedTextColor = lightGray,
                        ),
                        label = {
                            Text(text = stringResource(tab.titleId))
                        },
                        selected = stack.active.instance.toTab() == tab,
                        onClick = {
                            component.onTabSelected(tab)
                        },
                        icon = { Icon(imageVector = tab.imgVector, contentDescription = null) },
                    )
                }
            }
        }
    ) { padding ->
        Surface(
            color = MaterialTheme.colorScheme.onPrimary,
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
        ) {
            val activeInstance = stack.active.instance

            AnimatedContent(
                targetState = activeInstance,
                transitionSpec = {
                    // Анимация слайдов (горизонтальный переход)
                    slideInHorizontally(initialOffsetX = { it }) togetherWith
                            slideOutHorizontally(targetOffsetX = { -it }) using
                            SizeTransform { _, _ -> tween(durationMillis = 300) }
                },
                label = "MainTabTransition"
            ) { instance ->
                when (instance) {
                    is MainChild.Favourite -> {
                        FavouriteContent(instance.component)
                    }

                    is MainChild.Search -> {
                        SearchContent(instance.component)
                    }
                }
            }
        }
    }

}