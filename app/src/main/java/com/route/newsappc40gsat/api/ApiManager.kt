package com.route.newsappc40gsat.api

import android.util.Log
import com.route.newsappc40gsat.api.interceptors.AuthAPIKeyInterceptor
import com.route.newsappc40gsat.api.interceptors.GoogleMapsAPIKeyString
import com.route.newsappc40gsat.api.interceptors.NewsAppAPIKeyString
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ApiManager {
    @GoogleMapsAPIKeyString
    @Singleton
    @Provides
    fun provideGoogleMapsAPIKey(): String {
        return "akspfkoskflasdk"
    }

    @NewsAppAPIKeyString
    @Singleton
    @Provides
    fun provideAuthApiKey(): String {
        return "8e30e66ecc364d75967401f639e6f535"
    }

    @Provides
    @Singleton
    fun provideLoggingInterceptor(): HttpLoggingInterceptor {
        val httpLoggingInterceptor = HttpLoggingInterceptor { message ->
            Log.e("API", message)
        }
        httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
        return httpLoggingInterceptor
    }


    @Provides
    @Singleton
    fun provideAuthAPIKeyInterceptor(@NewsAppAPIKeyString apiKey: String): Interceptor {
        return AuthAPIKeyInterceptor(apiKey)
    }

    @Provides
    @Singleton
    fun provideOkHttpClient(
        authAPIKeyInterceptor: Interceptor,
        httpLoggingInterceptor: HttpLoggingInterceptor
    ): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(authAPIKeyInterceptor)
            .addInterceptor(httpLoggingInterceptor)
            .build()
    }

    @Provides
    @Singleton
    fun provideGsonConverterFactory(): GsonConverterFactory {
        return GsonConverterFactory.create()
    }

    @Provides
    @Singleton
    fun provideRetrofit(
        okHttpClient: OkHttpClient,
        gsonConverterFactory: GsonConverterFactory
    ): Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://newsapi.org/v2/")
            .client(okHttpClient)
            .addConverterFactory(gsonConverterFactory)
            .build()
    }

    @Provides
    @Singleton
    fun getNewsService(
        retrofit: Retrofit
    ): NewsService {
        return retrofit.create(NewsService::class.java)
    }

}