package ru.mavrinvladislav.sufttech25.main.presentation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Search
import androidx.compose.ui.graphics.vector.ImageVector
import ru.mavrinvladislav.sufttech25.R

sealed class MainTab(
    val titleId: Int,
    val imgVector: ImageVector
) {

    data object Search : MainTab(
        titleId = R.string.search,
        imgVector = Icons.Default.Search
    )

    data object Favourite : MainTab(
        titleId = R.string.favourite,
        imgVector = Icons.Default.Favorite
    )
}