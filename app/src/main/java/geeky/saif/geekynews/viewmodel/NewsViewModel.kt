package geeky.saif.geekynews.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import geeky.saif.geekynews.networksetup.repository.NewsRepository
import geeky.saif.geekynews.roomDb.NewsDao
import geeky.saif.geekynews.roomDb.NewsArticle
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NewsViewModel @Inject constructor(
    private val newsRepository: NewsRepository,
    private val newsDao: NewsDao
) : ViewModel() {

    private val _newsState = MutableStateFlow<List<NewsArticle>>(emptyList()) // Flow of news articles
    val newsState: StateFlow<List<NewsArticle>> get() = _newsState

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> get() = _isLoading

    init {
        // fetch latest news on initialization, fall back to cached if needed
        fetchLatestNews()
    }


    // Fetch latest news from the API and store it in the local database
    private fun fetchLatestNews() {
        _isLoading.value = true
        viewModelScope.launch {
            try {
                val response = newsRepository.getLatestNewsFromApi()
                if (response.results.isNotEmpty()) {
                    // Save fetched articles to the local database
                    newsRepository.saveNewsToLocalDb(response.results)
                    // Update state with fetched articles
                    _newsState.value = response.results.map {
                        NewsArticle(
                            title = it.title,
                            description = it.description,
                            imageUrl = it.image_url,
                            publishedAt = "" ,
                            article_id=it.article_id,
                            content = it.content
                        )
                    }
                }
            } catch (e: Exception) {
                // In case of error, try to load cached news
                loadCachedNews()
                e.printStackTrace()
            } finally {
                _isLoading.value = false
            }
        }
    }

    // Get cached news from the local database
    private fun loadCachedNews() {
        viewModelScope.launch {
            newsRepository.getCachedNews().collect { cachedNews ->
                if (cachedNews.isNotEmpty()) {
                    _newsState.value = cachedNews
                } else {
                    // Optionally, show a message for no cached news
                    _newsState.value = emptyList()
                }
            }
        }
    }

}
