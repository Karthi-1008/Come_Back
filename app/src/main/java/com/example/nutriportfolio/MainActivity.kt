package com.example.nutriportfolio

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.example.nutriportfolio.ui.root.AppRoot
import com.example.nutriportfolio.ui.root.AppStartupState
import com.example.nutriportfolio.ui.root.RootViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val rootViewModel: RootViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        val splashScreen = installSplashScreen()
        super.onCreate(savedInstanceState)

        // Keep system splash screen on screen until startup state is loaded from Room / DataStore
        splashScreen.setKeepOnScreenCondition {
            rootViewModel.startupState.value is AppStartupState.Loading
        }

        setContent {
            AppRoot(rootViewModel = rootViewModel)
        }
    }
}
