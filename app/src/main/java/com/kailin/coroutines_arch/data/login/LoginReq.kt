package com.kailin.coroutines_arch.data.login

data class LoginReq(
    val username: String,
    val password: String,
) {
    override fun toString(): String {
        return "LoginReqData(username='$username', password='$password')"
    }
}
