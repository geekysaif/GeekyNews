package geeky.saif.geekynews.di

import android.content.Context
import androidx.room.Room
import geeky.saif.geekynews.roomDb.NewsDatabase
import geeky.saif.geekynews.networksetup.NewsApiService
import geeky.saif.geekynews.networksetup.repository.NewsRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import geeky.saif.geekynews.roomDb.NewsDao
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton
import android.app.Application

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideApplicationContext(application: Application): Context {
        return application.applicationContext
    }

    @Provides
    @Singleton
    fun provideDatabase(context: Context): NewsDatabase {
        return Room.databaseBuilder(context, NewsDatabase::class.java, "news_db")
            .fallbackToDestructiveMigration()
            .build()
    }

    @Provides
    fun provideNewsDao(database: NewsDatabase) = database.newsDao()

    @Provides
    @Singleton
    fun provideRetrofit(): NewsApiService {
        return Retrofit.Builder()
            .baseUrl("https://newsdata.io/api/1/") // Correct base URL
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(NewsApiService::class.java)
    }

    @Provides
    @Singleton
    fun provideRepository(newsDao: NewsDao, apiService: NewsApiService): NewsRepository {
        return NewsRepository(apiService, newsDao)
    }
}
