package com.kailin.coroutines_arch.data.settings

import retrofit2.Response
import retrofit2.http.*

interface UserUpdateService {

    @PUT("/api/users/{objectId}")
    suspend fun update(
        @Path("objectId") objectId: String,
        @Body body: UserUpdateReq,
        @HeaderMap headerMap: Map<String, String>
    ): Response<UserUpdateInfo>
}