package ru.mavrinvladislav.sufttech25.common.presentation

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.launch
import ru.mavrinvladislav.sufttech25.common.ui.theme.SuftTech25Theme
import ru.mavrinvladislav.sufttech25.search.domain.repository.SearchRepository
import javax.inject.Inject

class MainActivity : ComponentActivity() {

    private val component by lazy {
        (application as BookApp).component
    }

    @Inject
    lateinit var repository: SearchRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        component.inject(this)
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        lifecycleScope.launch {
            val result = repository.fetchBooks("Harry")
            result.fold(
                onSuccess = {
                    Log.d("MainActivity", "Success: $it")
                },
                onFailure = {
                    Log.d("MainActivity", "Fail: $it")
                }
            )
        }

        setContent {
            SuftTech25Theme {
            }
        }
    }
}