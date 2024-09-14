package com.route.newsappc40gsat.api

import com.route.newsappc40gsat.model.Category
import com.route.newsappc40gsat.model.NewsResponse
import com.route.newsappc40gsat.model.SourcesResponse
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.Query

interface NewsService {

    @GET("top-headlines/sources")
    suspend fun getNewsSources(
        @Query("category") categoryId: String,
    ): Response<SourcesResponse>


    @GET("everything")
    suspend fun getNewsBySource(
        @Query("sources") sourceId: String
    ): Response<NewsResponse>
}
