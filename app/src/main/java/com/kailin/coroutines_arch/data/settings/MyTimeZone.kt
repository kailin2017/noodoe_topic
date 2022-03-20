package com.kailin.coroutines_arch.data.settings

data class MyTimeZone(
    val timeZone: String
) {
    override fun toString(): String {
        return "TimeZone(timeZone='$timeZone')"
    }
}