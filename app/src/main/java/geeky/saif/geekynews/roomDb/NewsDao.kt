package geeky.saif.geekynews.roomDb

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface NewsDao {
    @Insert
    suspend fun insertArticles(articles: List<NewsArticle>)

    @Query("SELECT * FROM news_article")
    fun getAllArticles(): Flow<List<NewsArticle>> // Return a Flow

    @Query("SELECT * FROM news_article WHERE article_id = :id")
    suspend fun getArticleById(id: String): NewsArticle?
}
