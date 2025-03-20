package ru.mavrinvladislav.sufttech25.details.presentation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import ru.mavrinvladislav.sufttech25.common.domain.model.Book

@Composable
fun DetailsContent(component: DetailsComponent) {

    val model by component.model.collectAsState()

    Scaffold(
        topBar = {
            TopBar(
                book = model.book,
                onBackButtonClick = {
                    component.onBackButtonClick()
                },
                onChangeStateClick = {
                    component.changeFavouriteBookState(book = model.book)
                }
            )
        }
    ) { padding ->
        Box(
            modifier = Modifier.padding(padding)
        ) {
            BookDetailScreen(book = model.book)
        }
    }
}

@Composable
fun BookDetailScreen(book: Book) {
    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            AsyncImage(
                model = book.img,
                contentDescription = null
            )
            Spacer(modifier = Modifier.size(14.dp))
            Text(
                text = book.authors.joinToString(", "),
                fontSize = 16.sp,
                color = Color.Gray,
            )
            Spacer(modifier = Modifier.size(8.dp))
            Text(
                text = book.title,
                fontSize = 20.sp,
                fontWeight = FontWeight.SemiBold
            )
            Spacer(modifier = Modifier.size(8.dp))
            Text(
                text = "1997 г.",
                fontSize = 14.sp,
                color = Color.Gray,
                modifier = Modifier.padding(top = 8.dp)
            )
        }
        Spacer(Modifier.size(22.dp))
        Card(
            modifier = Modifier.fillMaxSize(),
            shape = RoundedCornerShape(24.dp)
        ) {
            Column(
                Modifier.padding(20.dp)
            ) {
                Text(
                    text = book.publishedDate,
                    fontSize = 14.sp,
                    color = Color.Gray,
                    modifier = Modifier.padding(top = 8.dp)
                )
                Spacer(Modifier.size(16.dp))
                Text(
                    text = book.description,
                    fontSize = 14.sp,
                    color = Color.Gray,
                    modifier = Modifier.padding(top = 8.dp)
                )
            }
        }
//        Spacer(modifier = Modifier.padding(top = 32.dp))
//
//        // Секция описания
//        Text(
//            text = "Описание",
//            fontSize = 18.sp,
//            fontWeight = FontWeight.Medium,
//            modifier = Modifier.padding(bottom = 12.dp)
//        )
//
//        Text(
//            text = "Harry Potter has never even heard of Hogwarts when...",
//            fontSize = 14.sp,
//            color = Color.Gray,
//            lineHeight = 20.sp
//        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun TopBar(
    book: Book,
    onBackButtonClick: () -> Unit,
    onChangeStateClick: () -> Unit
) {
    TopAppBar(
        title = {},
        navigationIcon = {
            IconButton(
                onClick = {
                    onBackButtonClick()
                },
                content = {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = null
                    )
                },
            )
        },
        actions = {
            IconButton(
                onClick = onChangeStateClick
            ) {
                val icon =
                    if (book.isFavourite) Icons.Default.Favorite else Icons.Default.FavoriteBorder
                Icon(
                    imageVector = icon,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.background
                )
            }
        }
    )
}