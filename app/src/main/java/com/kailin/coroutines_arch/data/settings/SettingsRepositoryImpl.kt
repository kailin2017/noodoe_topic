package com.kailin.coroutines_arch.data.settings

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import com.kailin.coroutines_arch.data.RepoResult
import com.kailin.coroutines_arch.data.UserInfoDataSource
import com.kailin.coroutines_arch.data.login.UserInfo

class SettingsRepositoryImpl(
    private val myTimeZoneDataSource: MyTimeZoneDataSource,
    private val userUpdateDataSource: UserUpdateDataSource
) : SettingsRepository {

    private val timeZoneResult =
        MediatorLiveData<RepoResult<List<MyTimeZone>>>().also { liveData ->
            liveData.addSource(myTimeZoneDataSource.observerTimeZone()) {
                liveData.value = it
            }
        }

    override fun observerTimeZone() = timeZoneResult

    override fun observerUserUpdate() = userUpdateDataSource.observerUserUpdate()

    override fun userInfo(): LiveData<UserInfo> = UserInfoDataSource.userInfo

    override suspend fun fetchTimeZone() {
        myTimeZoneDataSource.fetchTimeZone()
    }

    override suspend fun setTimeZone(position: Int) {
        if (timeZoneResult.value !is RepoResult.Success) {
            return
        }
        val timeZone = (timeZoneResult.value as RepoResult.Success).data[position].timeZone
        val userName = userInfo().value?.objectId ?: ""
        userUpdateDataSource.userUpdate(userName, timeZone, UserInfoDataSource.getHeaderMap())

        val userInfo = userInfo().value ?: return
        userInfo.timeZone = timeZone
        UserInfoDataSource.setUserInfo(userInfo)
    }
}