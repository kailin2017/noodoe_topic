package com.kailin.coroutines_arch.data.home

import retrofit2.Response
import retrofit2.http.GET

interface TaipeiNewsService {

    @GET("https://tcgbusfs.blob.core.windows.net/dotapp/news.json")
    suspend fun fetchNews(): Response<TaipeiNews>
}