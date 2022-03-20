package com.kailin.coroutines_arch.utils.connect

import com.kailin.coroutines_arch.utils.GsonHelper
import okhttp3.ResponseBody
import java.lang.reflect.Type

fun <T> ResponseBody.toData(type: Type): T {
    return GsonHelper.fromJson(string(), type)
}