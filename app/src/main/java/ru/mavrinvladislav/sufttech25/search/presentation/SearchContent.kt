package ru.mavrinvladislav.sufttech25.search.presentation

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.PlatformTextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ru.mavrinvladislav.sufttech25.R
import ru.mavrinvladislav.sufttech25.common.ui.theme.brandBlue
import ru.mavrinvladislav.sufttech25.common.ui.theme.darkGray
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
            onClear = {
                component.clearQuery()
            },
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
                    },
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(top = 20.dp)
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
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(Modifier.weight(1f))
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
                    color = Color.White,
                    textAlign = TextAlign.Center
                )
            }
            Spacer(Modifier.weight(1f))
        }
}

@Composable
fun SearchCard(
    query: String,
    onClear: () -> Unit,
    onValueChange: (String) -> Unit,
    onKeyboardSearch: () -> Unit
) {
    val keyboardController = LocalSoftwareKeyboardController.current
    val focusManager = LocalFocusManager.current

    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = lightGray)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 16.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                // Иконка поиска
                Image(
                    painter = painterResource(R.drawable.ic_search_filled),
                    contentDescription = stringResource(R.string.search),
                    colorFilter = ColorFilter.tint(darkGray),
                    modifier = Modifier.size(24.dp)
                )

                // Кастомное поле ввода
                BasicTextField(
                    value = query,
                    onValueChange = onValueChange,
                    modifier = Modifier
                        .weight(1f)
                        .padding(start = 8.dp, end = 16.dp)
                        .height(40.dp),
                    textStyle = LocalTextStyle.current.copy(
                        color = Color.Black,
                        fontSize = 16.sp,
                        lineHeight = 20.sp,
                        platformStyle = PlatformTextStyle(
                            includeFontPadding = false // Убираем отступы шрифта
                        )
                    ),
                    cursorBrush = SolidColor(darkGray),
                    decorationBox = { innerTextField ->
                        Box(
                            contentAlignment = Alignment.CenterStart,
                            modifier = Modifier.fillMaxSize()
                        ) {
                            if (query.isEmpty()) {
                                Text(
                                    text = stringResource(R.string.search),
                                    color = darkGray.copy(alpha = 0.6f),
                                    fontSize = 16.sp
                                )
                            }
                            innerTextField()
                        }
                    },
                    keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
                    keyboardActions = KeyboardActions(
                        onSearch = {
                            onKeyboardSearch()
                            keyboardController?.hide()
                            focusManager.clearFocus()
                        }
                    ),
                    singleLine = true
                )
                IconButton(
                    onClick = {
                        onClear()
                    }
                ) {
                    Icon(
                        modifier = Modifier.size(24.dp),
                        tint = darkGray,
                        imageVector = Icons.Default.Clear,
                        contentDescription = stringResource(R.string.clear)
                    )
                }
            }
        }
    }
}