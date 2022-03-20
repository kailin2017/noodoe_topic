package com.kailin.coroutines_arch.data.login

import androidx.lifecycle.LiveData
import com.kailin.coroutines_arch.data.RepoResult

interface LoginRepository {

    fun observerLogin(): LiveData<RepoResult<UserInfo>>

    suspend fun login(username: String, password: String)
}