package com.example.moviesapp.di

import android.app.Application
import android.content.Context
import androidx.room.Room
import com.example.moviesapp.MoviesApplication
import com.example.moviesapp.data.api.APIService
import com.example.moviesapp.data.model.database.AppDatabase
import com.example.moviesapp.data.model.database.MovieDao
import com.example.moviesapp.data.repository.movierepository.MoviesRepositoryDatabase
import com.example.moviesapp.domain.popularmoviesusecase.GetNowPlayingMoviesUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideGetNowPlayingMoviesUseCase(apiService: APIService): GetNowPlayingMoviesUseCase =
        GetNowPlayingMoviesUseCase(apiService)

    @Provides
    @Singleton
    fun provideMoviesApplication(application: Application): MoviesApplication {
        return application as MoviesApplication
    }

    @Provides
    @Singleton
    fun provideContext(application: MoviesApplication): Context {
        return application.applicationContext
    }

    @Provides
    @Singleton
    fun provideAppDatabase(context: Context): AppDatabase {
        return Room.databaseBuilder(
            context.applicationContext,
            AppDatabase::class.java,
            "movies_db")
            .build()
    }

    @Provides
    @Singleton
    fun provideMovieDao(appDatabase: AppDatabase): MovieDao {
        return appDatabase.movieDao()
    }

    @Provides
    @Singleton
    fun provideMoviesRepositoryDatabase(movieDao: MovieDao): MoviesRepositoryDatabase {
        return MoviesRepositoryDatabase(movieDao)
    }

    @Provides
    @Singleton
    fun provideOkHttpClient(): OkHttpClient {
        val builder = OkHttpClient.Builder()
            .readTimeout(30, TimeUnit.SECONDS)
            .writeTimeout(30, TimeUnit.SECONDS)
            .connectTimeout(30, TimeUnit.SECONDS)
        return builder.build()
    }

    @Provides
    @Singleton
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit = Retrofit.Builder()
        .baseUrl("https://api.themoviedb.org/3/")
        .client(okHttpClient)
        .addConverterFactory(ScalarsConverterFactory.create())
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    @Provides
    @Singleton
    fun provideMovieApi(retrofit: Retrofit): APIService = retrofit.create(APIService::class.java)
}
