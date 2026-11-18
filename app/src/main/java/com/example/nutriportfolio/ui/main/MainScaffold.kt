package com.example.nutriportfolio.ui.main

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.nutriportfolio.R
import com.example.nutriportfolio.domain.model.UserProfile
import com.example.nutriportfolio.ui.settings.CreditsScreen
import com.example.nutriportfolio.ui.settings.EditProfileScreen
import com.example.nutriportfolio.ui.settings.SettingsScreen
import com.example.nutriportfolio.ui.settings.SettingsViewModel
import com.example.nutriportfolio.ui.theme.MotionTokens

enum class MainTab {
    TODAY, ADD_FOOD, PORTFOLIO, SETTINGS
}

private enum class SettingsSubScreen {
    MAIN, EDIT_PROFILE, CREDITS
}

@Composable
fun MainScaffold(
    profile: UserProfile?,
    modifier: Modifier = Modifier
) {
    var selectedTab by rememberSaveable { mutableStateOf(MainTab.TODAY) }
    var settingsSubScreen by rememberSaveable { mutableStateOf(SettingsSubScreen.MAIN) }
    val haptic = LocalHapticFeedback.current

    val settingsViewModel: SettingsViewModel = hiltViewModel()

    Scaffold(
        modifier = modifier.fillMaxSize(),
        bottomBar = {
            // Show bottom bar only when on main tabs (not inside settings sub-screens)
            if (selectedTab != MainTab.SETTINGS || settingsSubScreen == SettingsSubScreen.MAIN) {
                NavigationBar(
                    containerColor = MaterialTheme.colorScheme.surface,
                    tonalElevation = 4.dp
                ) {
                    val tabs = listOf(
                        MainTab.TODAY to Pair(R.drawable.ic_tab_today, R.string.tab_today),
                        MainTab.ADD_FOOD to Pair(R.drawable.ic_tab_add, R.string.tab_add),
                        MainTab.PORTFOLIO to Pair(R.drawable.ic_tab_portfolio, R.string.tab_portfolio),
                        MainTab.SETTINGS to Pair(R.drawable.ic_tab_settings, R.string.tab_settings)
                    )

                    tabs.forEach { (tab, assetAndLabel) ->
                        val isSelected = selectedTab == tab
                        NavigationBarItem(
                            selected = isSelected,
                            onClick = {
                                if (selectedTab != tab) {
                                    haptic.performHapticFeedback(HapticFeedbackType.TextHandleMove)
                                    selectedTab = tab
                                    if (tab == MainTab.SETTINGS) {
                                        settingsSubScreen = SettingsSubScreen.MAIN
                                    }
                                }
                            },
                            icon = {
                                Icon(
                                    painter = painterResource(id = assetAndLabel.first),
                                    contentDescription = stringResource(id = assetAndLabel.second),
                                    modifier = Modifier.size(22.dp)
                                )
                            },
                            label = {
                                Text(
                                    text = stringResource(id = assetAndLabel.second),
                                    style = MaterialTheme.typography.labelMedium
                                )
                            },
                            colors = NavigationBarItemDefaults.colors(
                                selectedIconColor = MaterialTheme.colorScheme.primary,
                                selectedTextColor = MaterialTheme.colorScheme.primary,
                                unselectedIconColor = MaterialTheme.colorScheme.onSurfaceVariant,
                                unselectedTextColor = MaterialTheme.colorScheme.onSurfaceVariant,
                                indicatorColor = MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.5f)
                            )
                        )
                    }
                }
            }
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            AnimatedContent(
                targetState = selectedTab,
                transitionSpec = {
                    MotionTokens.ScreenEnterTransition togetherWith MotionTokens.ScreenExitTransition
                },
                label = "tabTransition"
            ) { targetTab ->
                when (targetTab) {
                    MainTab.TODAY -> TodayPlaceholder(profile = profile)
                    MainTab.ADD_FOOD -> AddFoodPlaceholder()
                    MainTab.PORTFOLIO -> PortfolioPlaceholder()
                    MainTab.SETTINGS -> {
                        when (settingsSubScreen) {
                            SettingsSubScreen.MAIN -> SettingsScreen(
                                viewModel = settingsViewModel,
                                onNavigateToEditProfile = {
                                    settingsSubScreen = SettingsSubScreen.EDIT_PROFILE
                                },
                                onNavigateToCredits = {
                                    settingsSubScreen = SettingsSubScreen.CREDITS
                                }
                            )
                            SettingsSubScreen.EDIT_PROFILE -> {
                                if (profile != null) {
                                    EditProfileScreen(
                                        currentProfile = profile,
                                        onSaveProfile = { updated ->
                                            settingsViewModel.updateProfile(updated) {
                                                settingsSubScreen = SettingsSubScreen.MAIN
                                            }
                                        },
                                        onNavigateBack = {
                                            settingsSubScreen = SettingsSubScreen.MAIN
                                        }
                                    )
                                } else {
                                    settingsSubScreen = SettingsSubScreen.MAIN
                                }
                            }
                            SettingsSubScreen.CREDITS -> CreditsScreen(
                                onNavigateBack = {
                                    settingsSubScreen = SettingsSubScreen.MAIN
                                }
                            )
                        }
                    }
                }
            }
        }
    }
}
