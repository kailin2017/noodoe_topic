package com.kailin.coroutines_arch.data.settings

import androidx.lifecycle.LiveData
import com.kailin.coroutines_arch.data.RepoResult
import com.kailin.coroutines_arch.data.login.UserInfo

interface SettingsRepository {

    fun observerTimeZone(): LiveData<RepoResult<List<MyTimeZone>>>

    fun observerUserUpdate(): LiveData<RepoResult<UserUpdateInfo>>

    fun userInfo(): LiveData<UserInfo>

    suspend fun fetchTimeZone()

    suspend fun setTimeZone(position: Int)
}