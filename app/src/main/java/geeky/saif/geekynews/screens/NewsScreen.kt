package geeky.saif.geekynews.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import geeky.saif.geekynews.viewmodel.NewsViewModel
import geeky.saif.geekynews.roomDb.NewsArticle
import geeky.saif.geekynews.utility.loadImage.LoadImage
import androidx.compose.ui.Alignment
import androidx.navigation.NavController
import geeky.saif.geekynews.utility.AppConstant.AppConstants

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NewsScreen(viewModel: NewsViewModel = hiltViewModel(), navController: NavController) {
    val newsState by viewModel.newsState.collectAsState(initial = emptyList())
    val isLoading by viewModel.isLoading.collectAsState(initial = false)

    var expanded by remember { mutableStateOf(false) }

    // Scaffold to handle the top app bar and body content
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Box(
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(
                            text = AppConstants.NEWS,
                            modifier = Modifier.align(Alignment.Center)
                        )
                    }
                        },
                navigationIcon = {
                    IconButton(onClick = {  }) {
                        Icon(Icons.Filled.Menu, contentDescription = "Open Drawer")
                    }
                },
                actions = {
                    // Menu button (overflow menu)
                    IconButton(onClick = { expanded = !expanded }) {
                        Icon(Icons.Filled.MoreVert, contentDescription = "Menu")
                    }

                }
            )
        }
    ) { paddingValues ->
        // Show loading indicator while fetching data
        if (isLoading) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp)
            ) {
                CircularProgressIndicator(
                    modifier = Modifier
                        .align(Alignment.Center)
                )
            }
        } else {
            // Show articles UI
            if (newsState.isNotEmpty()) {
                LazyColumn(
                    modifier = Modifier.padding(paddingValues)
                ) {
                    items(newsState) { article ->
                        NewsItem(article, navController)
                    }
                }
            } else {
                Text(
                    text = "No articles available.",
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier.padding(paddingValues)
                )
            }
        }
    }
}


@Composable
fun NewsItem(article: NewsArticle, navController: NavController) {
    Card(
        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth()
            .clickable {
                // Navigate to the detail screen and pass the id
                navController.navigate("detail/${article.article_id}")
            }
    ) {
        Column(Modifier.padding(8.dp)) {
            if (article.imageUrl != null) {
                LoadImage(article.imageUrl)
            }
            Text(text = article.title, style = MaterialTheme.typography.headlineSmall)
            Text(text = article.description ?: "", style = MaterialTheme.typography.bodyLarge)
        }
    }
}

