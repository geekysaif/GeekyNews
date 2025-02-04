package geeky.saif.geekynews.networksetup.responses

data class NewsResponse(
  val results: List<Article> // Change to List<Article>
)

data class Article(
  val title: String,
  val description: String?,
  val link: String,
  val image_url: String?,
  val article_id: String?,
  val content: String?,
)
