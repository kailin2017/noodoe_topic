package com.kailin.coroutines_arch.data.home

import androidx.lifecycle.LiveData
import com.kailin.coroutines_arch.data.RepoResult
import com.kailin.coroutines_arch.data.login.UserInfo

interface HomeRepository {

    fun observerNews(): LiveData<RepoResult<TaipeiNews>>

    suspend fun fetchNews()

    fun userInfo(): LiveData<UserInfo>
}