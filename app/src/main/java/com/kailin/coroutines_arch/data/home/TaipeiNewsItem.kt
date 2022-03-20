package com.kailin.coroutines_arch.data.home

import com.kailin.coroutines_arch.utils.toDate
import java.util.*

data class TaipeiNewsItem(
    val chtmessage: String,
    val content: String,
    val endtime: String,
    val engmessage: String,
    val starttime: String,
    val updatetime: String,
    val url: String,
) {
    override fun toString(): String {
        return "TaipeiNewsItem(chtmessage='$chtmessage', content='$content', endtime='$endtime', engmessage='$engmessage', starttime='$starttime', updatetime='$updatetime', url='$url')"
    }

    fun isValid(time: String): Boolean {
        return isValid(time.toDate())
    }

    fun isValid(date: Date?): Boolean {
        date ?: return false
        val start: Date = starttime.toDate() ?: return false
        val end: Date = endtime.toDate() ?: return false
        return start.time < date.time && date.time < end.time
    }
}