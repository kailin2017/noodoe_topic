package com.kailin.coroutines_arch.data.login

import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.HeaderMap
import retrofit2.http.POST

interface LoginService {

    @POST("/api/login")
    suspend fun login(
        @Body data: LoginReq,
        @HeaderMap headerMap: Map<String, String>
    ): Response<UserInfo>
}