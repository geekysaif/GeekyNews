package geeky.saif.geekynews.screens

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.material3.*
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import geeky.saif.geekynews.utility.AppConstant.AppConstants
import geeky.saif.geekynews.utility.loadImage.LoadImage
import geeky.saif.geekynews.viewmodel.NewsDetailViewModel

@OptIn(ExperimentalMaterial3Api::class)

@Composable
fun NewsDetailScreen(articleId: String, navController: NavController, viewModel: NewsDetailViewModel = hiltViewModel())
{
    val articleId = articleId
    Log.d("articleIdLong:---", articleId)
    // Fetch the article if not already fetched
    LaunchedEffect(articleId) {
        viewModel.fetchArticleById(articleId)
    }

    // Observe the article state from ViewModel
    val article by viewModel.articleState.collectAsState()

    // Scaffold with a top bar and article details
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = AppConstants.NEWSDETAILS) },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .padding(16.dp)
        ) {
            // Check if article is available and display its details
            if (article != null) {
                article?.imageUrl?.let { LoadImage(it) }
                Text(text = "${article?.title}", style = MaterialTheme.typography.headlineSmall)
                Text(text = article?.description ?: "No description available.", style = MaterialTheme.typography.bodyMedium)

            } else {
                // Show loading or error message
                Text(text = "Loading article details...", style = MaterialTheme.typography.bodyLarge)
            }
        }
    }
}

