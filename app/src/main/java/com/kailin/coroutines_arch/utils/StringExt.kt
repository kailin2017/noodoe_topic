package com.kailin.coroutines_arch.utils

import java.text.SimpleDateFormat
import java.util.*
import java.util.regex.Pattern

fun String.toDate(datePattern: String = "yyyy-MM-dd HH:mm:ss"): Date? {
    return try {
        SimpleDateFormat(datePattern).parse(this)
    } catch (e: Exception) {
        e.printStackTrace()
        null
    }
}

fun String.isEmail(): Boolean {
    return Pattern.compile("^(.+)@(.+)$").matcher(this).matches()
}