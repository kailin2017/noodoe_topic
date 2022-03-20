package com.kailin.coroutines_arch.data.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import com.kailin.coroutines_arch.data.RepoResult
import com.kailin.coroutines_arch.data.UserInfoDataSource
import com.kailin.coroutines_arch.data.login.UserInfo

class HomeRepositoryImpl(
    private val taipeiNewsDataSource: TaipeiNewsDataSource,
) : HomeRepository {

    private val observerNewsRepo = MediatorLiveData<RepoResult<TaipeiNews>>().also { liveData->
        liveData.addSource(taipeiNewsDataSource.observerNews()){
            liveData.value = it
        }
    }

    override fun observerNews(): LiveData<RepoResult<TaipeiNews>> = observerNewsRepo

    override suspend fun fetchNews() {
        taipeiNewsDataSource.fetchNews()
    }

    override fun userInfo(): LiveData<UserInfo> = UserInfoDataSource.userInfo
}