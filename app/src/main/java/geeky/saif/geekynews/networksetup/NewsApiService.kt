package geeky.saif.geekynews.networksetup

import geeky.saif.geekynews.networksetup.responses.NewsResponse
import geeky.saif.geekynews.utility.AppConstant.AppConstants
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsApiService {
    @GET("latest")
    suspend fun getNews(
        @Query("apikey") apiKey: String = AppConstants.API_KEY,
        @Query("category") category: String = AppConstants.CATEGORY,
        @Query("country") country: String = AppConstants.COUNTRY
    ): NewsResponse
}


