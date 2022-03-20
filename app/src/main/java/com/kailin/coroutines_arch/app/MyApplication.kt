package com.kailin.coroutines_arch.app

import android.app.Application
import com.kailin.coroutines_arch.data.UserInfoDataSource

class MyApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        instance = this
        UserInfoDataSource.clearUserInfo()
    }

    companion object {
        lateinit var instance: MyApplication
    }
}