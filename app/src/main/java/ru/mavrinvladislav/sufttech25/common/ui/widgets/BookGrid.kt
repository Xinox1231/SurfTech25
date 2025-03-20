package ru.mavrinvladislav.sufttech25.common.ui.widgets

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import ru.mavrinvladislav.sufttech25.common.domain.model.Book

@Composable
fun BooksGrid(
    books: List<Book>,
    onFavoriteClick: (Book) -> Unit,
    onCardClick: (Book) -> Unit
) {
    LazyVerticalGrid(
        modifier = Modifier
            .fillMaxSize()
            .padding(vertical = 20.dp),
        columns = GridCells.Fixed(2), // 2 колонки
        verticalArrangement = Arrangement.spacedBy(32.dp),
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        items(books) { book ->
            BookCard(
                book = book,
                onFavoriteClick = onFavoriteClick,
                onCardClick = onCardClick
            )
        }
    }
}