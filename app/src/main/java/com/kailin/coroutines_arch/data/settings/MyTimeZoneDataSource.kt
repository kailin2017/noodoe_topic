package com.kailin.coroutines_arch.data.settings

import androidx.lifecycle.LiveData
import com.kailin.coroutines_arch.data.RepoResult

interface MyTimeZoneDataSource {

    fun observerTimeZone(): LiveData<RepoResult<List<MyTimeZone>>>

    suspend fun fetchTimeZone()
}