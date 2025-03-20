package ru.mavrinvladislav.sufttech25.search.presentation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import ru.mavrinvladislav.sufttech25.R
import ru.mavrinvladislav.sufttech25.common.ui.theme.brandBlue
import ru.mavrinvladislav.sufttech25.common.ui.theme.lightGray
import ru.mavrinvladislav.sufttech25.common.ui.widgets.BooksGrid

@Composable
fun SearchContent(component: SearchComponent) {

    val model by component.model.collectAsState()

    Column(
        modifier = Modifier.padding(horizontal = 20.dp, vertical = 8.dp)
    ) {
        SearchCard(
            query = model.query,
            onValueChange = {
                component.updateSearchQuery(it)
            },
            onKeyboardSearch = {
                component.startSearching()
            }
        )

        when (val state = model.searchState) {
            is SearchStore.State.SearchState.BooksFound -> {
                BooksGrid(
                    books = state.booksList,
                    onFavoriteClick = {
                        component.changeFavouriteStatus(it)
                    },
                    onCardClick = {
                        component.clickOnBook(it)
                    }
                )
            }

            is SearchStore.State.SearchState.EmptyState -> {
                EmptyState()
            }

            is SearchStore.State.SearchState.Error -> {
                ErrorState {
                    component.startSearching()
                }
            }

            is SearchStore.State.SearchState.NotFound -> {
                NotFoundState()
            }

            is SearchStore.State.SearchState.Loading -> {
                LoadingState()
            }
        }
    }
}

@Composable
private fun EmptyState() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text(text = stringResource(R.string.empty_state))
    }
}

@Composable
private fun NotFoundState() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text(text = stringResource(R.string.not_found_state))
    }
}

@Composable
private fun LoadingState() {
    Box(modifier = Modifier.fillMaxSize()) {
        CircularProgressIndicator(
            modifier = Modifier.align(Alignment.Center)
        )
    }
}

@Composable
private fun ErrorState(
    onRetryButtonClick: () -> Unit
) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = stringResource(R.string.error_message),
            style = MaterialTheme.typography.bodyMedium,
        )
        Spacer(modifier = Modifier.height(16.dp)) // Отступ между текстом и кнопкой
        Button(
            onClick = onRetryButtonClick,
            modifier = Modifier
                .padding(8.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = brandBlue,
                contentColor = Color.White
            ),
            shape = RoundedCornerShape(8.dp)
        ) {
            Text(
                text = stringResource(R.string.retry_button_text),
                style = MaterialTheme.typography.bodyMedium,
                color = Color.White
            )
        }
    }
}

@Composable
private fun SearchCard(
    query: String,
    onValueChange: (String) -> Unit,
    onKeyboardSearch: () -> Unit
) {
    Card(
        colors = CardDefaults.cardColors(
            containerColor = lightGray,
        ),
        modifier = Modifier.clip(RoundedCornerShape(8.dp))
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                modifier = Modifier.padding(
                    vertical = 8.dp,
                    horizontal = 16.dp
                ),
                imageVector = Icons.Default.Search,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.background
            )
            TextField(
                value = query,
                onValueChange = { onValueChange(it) },
                modifier = Modifier.padding(end = 16.dp),
                placeholder = { Text(text = stringResource(R.string.search)) },
                keyboardOptions = KeyboardOptions(
                    imeAction = ImeAction.Search
                ),
                keyboardActions = KeyboardActions(
                    onSearch = {
                        onKeyboardSearch()
                    }
                )
            )
        }
    }
}