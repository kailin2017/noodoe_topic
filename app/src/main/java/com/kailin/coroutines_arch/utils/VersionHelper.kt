package com.kailin.coroutines_arch.utils

import com.kailin.coroutines_arch.BuildConfig

object VersionHelper {

    @JvmStatic
    fun compareVersion(compareVersion: String): Boolean {
        var isNewVersion = false
        try {
            val appVersionInt = Integer.parseInt(BuildConfig.VERSION_NAME.replace(".", ""))
            val compareVersionInt = Integer.parseInt(compareVersion.replace(".", ""))
            if (compareVersionInt > appVersionInt) {
                isNewVersion = true
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return isNewVersion
    }
}