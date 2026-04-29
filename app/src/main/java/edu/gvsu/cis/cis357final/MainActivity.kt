package edu.gvsu.cis.cis357final

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import edu.gvsu.cis.composenavigation.ui.Main
import edu.gvsu.cis.composenavigation.ui.FruitScreen
import edu.gvsu.cis.composenavigation.ui.SettingsScreen
import edu.gvsu.cis.cis357final.ui.theme.CIS357FINALTheme
import edu.gvsu.cis.cis357final.viewmodel.MyViewModel

class MainActivity : ComponentActivity() {

    val viewModel by viewModels<MyViewModel>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            CIS357FINALTheme {
                val navController = rememberNavController()

                Scaffold(modifier = Modifier.fillMaxSize()) { paddingValues ->
                    NavHost(
                        navController = navController,
                        startDestination = Route.MainScreen,
                        modifier = Modifier.padding(paddingValues)
                    ) {

                        composable(Route.MainScreen) {
                            MainScreen(
                                navController = navController,
                                myViewModel = viewModel, // Pass the viewModel to MainScreen
                                goToSettings = {
                                    // Navigate to SettingsScreen
                                    navController.navigate(Route.SettingsScreen)
                                },
                                goToFruitScreen = { fruitName ->
                                    navController.navigate(Route.FruitScreen(fruitName))
                                }
                            )
                        }

                        composable(Route.FruitScreen::class.java.simpleName) { backStackEntry ->
                            val route = backStackEntry.toRoute<Route.FruitScreen>()
                            FruitScreen(
                                fruitName = route.fruitName,
                                navController = navController,
                                viewModel = viewModel
                            )
                        }

                        composable(Route.SettingsScreen::class.java.simpleName) {
                            SettingsScreen(
                                viewModel = viewModel,
                                onBack = { navController.popBackStack() }
                            )
                        }
                    }
                }
            }
        }
    }
}