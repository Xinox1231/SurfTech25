package ru.mavrinvladislav.sufttech25.main.presentation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Divider
import androidx.compose.material3.HorizontalDivider
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
import androidx.compose.ui.unit.dp
import com.arkivanov.decompose.extensions.compose.jetpack.subscribeAsState
import ru.mavrinvladislav.sufttech25.common.ui.theme.brandBlue
import ru.mavrinvladislav.sufttech25.favourite.presentation.FavouriteContent
import ru.mavrinvladislav.sufttech25.search.presentation.SearchContent

@Composable
fun MainContent(component: MainComponent) {

    val stack by component.stackNavigation.subscribeAsState()

    Scaffold(
        bottomBar = {
            BottomAppBar(
                containerColor = MaterialTheme.colorScheme.onPrimary,
            ) {
                val tabs = listOf(MainTab.Search, MainTab.Favourite)
                tabs.forEach { tab ->
                    NavigationBarItem(
                        colors = NavigationBarItemDefaults.colors(
                            selectedIconColor = brandBlue,
                            selectedTextColor = brandBlue,
                            unselectedIconColor = MaterialTheme.colorScheme.secondary,
                            unselectedTextColor = MaterialTheme.colorScheme.secondary,
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
            when (val instance = stack.active.instance) {
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