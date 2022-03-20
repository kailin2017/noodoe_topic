package com.kailin.coroutines_arch.data.home

import androidx.lifecycle.LiveData
import com.kailin.coroutines_arch.data.RepoResult

interface TaipeiNewsDataSource {

    fun observerNews(): LiveData<RepoResult<TaipeiNews>>

    suspend fun fetchNews()
}