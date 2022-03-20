package com.kailin.coroutines_arch.data.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import com.kailin.coroutines_arch.data.RepoResult
import com.kailin.coroutines_arch.data.UserInfoDataSource

class LoginRepositoryImpl(
    private val dataSource: LoginDataSource,
) : LoginRepository {

    private val loginResult = MediatorLiveData<RepoResult<UserInfo>>().also { liveData ->
        liveData.addSource(dataSource.observerLogin()) {
            liveData.value = it
            if (it is RepoResult.Success) {
                UserInfoDataSource.setUserInfo(it.data)
            }
        }
    }

    override fun observerLogin(): LiveData<RepoResult<UserInfo>> = loginResult

    override suspend fun login(username: String, password: String) {
        dataSource.login(username, password)
    }
}