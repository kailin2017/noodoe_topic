package com.kailin.coroutines_arch.data.error

data class ErrorRespData(
    val code: Int,
    val error: String
) {
    override fun toString(): String {
        return "RespData(code=$code, error='$error')"
    }
}