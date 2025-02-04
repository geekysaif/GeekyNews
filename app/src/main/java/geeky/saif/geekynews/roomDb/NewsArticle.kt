package geeky.saif.geekynews.roomDb

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "news_article")

data class NewsArticle(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val title: String,
    val description: String?,
    val imageUrl: String?,
    val publishedAt: String?,
    val article_id: String?,
    val content: String?
)
