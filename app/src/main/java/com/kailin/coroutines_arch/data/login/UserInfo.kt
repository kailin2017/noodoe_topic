package com.kailin.coroutines_arch.data.login

data class UserInfo(
    val code: String = "",
    val createdAt: String = "",
    val isVerifiedReportEmail: Boolean = false,
    val number: Int = 0,
    val objectId: String = "",
    val parameter: Int = 0,
    val phone: String = "",
    val reportEmail: String = "",
    val sessionToken: String = "",
    var timeZone: String = "",
    val timezone: Int = 0,
    val timone: String = "",
    val updatedAt: String = "",
    val username: String = ""
) {
    override fun toString(): String {
        return "UserInfo(code='$code', createdAt='$createdAt', isVerifiedReportEmail=$isVerifiedReportEmail, number=$number, objectId='$objectId', parameter=$parameter, phone='$phone', reportEmail='$reportEmail', sessionToken='$sessionToken', timeZone='$timeZone', timezone=$timezone, timone='$timone', updatedAt='$updatedAt', username='$username')"
    }
}