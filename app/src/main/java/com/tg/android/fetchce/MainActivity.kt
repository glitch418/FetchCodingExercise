package com.tg.android.fetchce

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import com.tg.android.fetchce.di.NetworkModule
import com.tg.android.fetchce.ui.screens.home.HomeScreen
import com.tg.android.fetchce.ui.screens.splash.SplashScreen
import com.tg.android.fetchce.viewmodels.ItemViewModel
import com.tg.android.fetchce.ui.theme.FetchCETheme

/**
 * Main Activity that sets up UI and dependencies (could use Hilt).
 * Contains a splash screen that transitions to the HomeScreen.
 */
class MainActivity : ComponentActivity() {

    private lateinit var viewModel: ItemViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Initialize dependencies
        setupDependencies()

        setContent {
            FetchCETheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    var showSplash by rememberSaveable { mutableStateOf(true) }

                    if (showSplash) {
                        SplashScreen(durationMillis = 1000) {
                            showSplash = false
                        }
                    } else {
                        HomeScreen(viewModel = viewModel)
                    }
                }
            }
        }
    }

    private fun setupDependencies() {
        val okHttpClient = NetworkModule.provideOkHttpClient()
        val retrofit = NetworkModule.provideRetrofit(okHttpClient)
        val fetchApi = NetworkModule.provideFetchApi(retrofit)
        val repository = NetworkModule.provideItemRepository(fetchApi)
        val getItemsUseCase = NetworkModule.provideGetItemsUseCase(repository)
        val sortItemsUseCase = NetworkModule.provideSortItemsUseCase()

        viewModel = ItemViewModel(getItemsUseCase, sortItemsUseCase)
    }
}