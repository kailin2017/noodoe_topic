package com.kailin.coroutines_arch.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.kailin.coroutines_arch.BuildConfig
import com.kailin.coroutines_arch.data.login.UserInfo

object UserInfoDataSource {

    private const val APIKEY = BuildConfig.WATCH_MASTER_KEY

    private val _userInfo = MutableLiveData<UserInfo>()
    val userInfo: LiveData<UserInfo> = _userInfo

    fun setUserInfo(userData: UserInfo) {
        _userInfo.postValue(userData)
    }

    fun clearUserInfo() {
        _userInfo.postValue(UserInfo())
    }

    fun getHeaderMap(): HashMap<String, String> {
        val headerMap = HashMap<String, String>()
        headerMap["X-Parse-Application-Id"] = APIKEY
        userInfo.value?.let {
            headerMap["X-Parse-Session-Token"] = it.sessionToken
        }
        return headerMap
    }
}