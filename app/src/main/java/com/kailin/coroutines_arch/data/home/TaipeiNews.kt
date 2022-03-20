package com.kailin.coroutines_arch.data.home

data class TaipeiNews(
    val News: List<TaipeiNewsItem>,
    val updateTime: String
) {
    override fun toString(): String {
        return "TaipeiNews(News=$News, updateTime='$updateTime')"
    }
}