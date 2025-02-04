package geeky.saif.geekynews.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import geeky.saif.geekynews.roomDb.NewsArticle
import geeky.saif.geekynews.roomDb.NewsDao
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NewsDetailViewModel @Inject constructor(
    private val newsDao: NewsDao
) : ViewModel() {

    // State to hold the article data
    private val _articleState = MutableStateFlow<NewsArticle?>(null)
    val articleState: StateFlow<NewsArticle?> = _articleState

    // Function to fetch article by ID
    fun fetchArticleById(id: String) {
        viewModelScope.launch {
            val article = newsDao.getArticleById(id)
            _articleState.value = article
        }
    }
}
