package com.kailin.coroutines_arch.utils

import com.google.gson.Gson
import java.lang.reflect.Type

object GsonHelper {

    val gson = Gson()

    fun <T> fromJson(json: String, type: Type): T = gson.fromJson(json, type)
}