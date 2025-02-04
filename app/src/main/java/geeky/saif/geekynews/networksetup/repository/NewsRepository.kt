package geeky.saif.geekynews.networksetup.repository

import geeky.saif.geekynews.networksetup.NewsApiService
import geeky.saif.geekynews.roomDb.NewsDao
import geeky.saif.geekynews.roomDb.NewsArticle
import geeky.saif.geekynews.networksetup.responses.NewsResponse
import geeky.saif.geekynews.networksetup.responses.Article

import javax.inject.Inject

class NewsRepository @Inject constructor(
    private val apiService: NewsApiService,
    private val newsDao: NewsDao
) {
    suspend fun getLatestNewsFromApi(): NewsResponse {
        return apiService.getNews() // Fetch from API
    }

    suspend fun saveNewsToLocalDb(articles: List<Article>) {
        val newsArticles = articles.map { article ->
            NewsArticle(
                title = article.title,
                description = article.description,
                imageUrl = article.image_url,
                publishedAt = "" ,
                article_id = article.article_id,
                content = article.content
            )
        }
        // Save to the local database
        newsDao.insertArticles(newsArticles)
    }

    // Get cached news from the database
    fun getCachedNews() = newsDao.getAllArticles()

    suspend fun getArticleById(id: String): NewsArticle? {
        return newsDao.getArticleById(id)  // Assuming you've defined this function in NewsDao
    }

}
