package geeky.saif.geekynews

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import dagger.hilt.android.AndroidEntryPoint
import geeky.saif.geekynews.ui.theme.GeekyNewsTheme
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import geeky.saif.geekynews.screens.NewsDetailScreen
import geeky.saif.geekynews.screens.NewsScreen

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            GeekyNewsTheme {
                // Call the NewsScreen Composable
                // NewsScreen()
                AppNavHost()
            }
        }
    }

    @Composable
    fun AppNavHost() {
        val navController = rememberNavController()

        Scaffold { innerPadding ->
            NavHost(
                navController = navController,
                startDestination = "news_screen",
                modifier = Modifier.padding(innerPadding)
            ) {
                composable("news_screen") {
                    NewsScreen(navController = navController) // Pass navController to NewsScreen
                }
                composable("detail/{articleId}") { backStackEntry ->
                    val articleId = backStackEntry.arguments?.getString("articleId") ?: "No title"
                    NewsDetailScreen(articleId = articleId, navController = navController)
                }

            }
        }
    }

}



