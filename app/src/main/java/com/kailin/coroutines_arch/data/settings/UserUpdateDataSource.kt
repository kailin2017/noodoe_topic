package com.kailin.coroutines_arch.data.settings

import androidx.lifecycle.LiveData
import com.kailin.coroutines_arch.data.RepoResult

interface UserUpdateDataSource {

    fun observerUserUpdate(): LiveData<RepoResult<UserUpdateInfo>>

    suspend fun userUpdate(userName: String, timeZone: String, headerMap: Map<String, String>)
}