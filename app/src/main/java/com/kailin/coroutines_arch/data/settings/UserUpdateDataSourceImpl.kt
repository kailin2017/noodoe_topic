package com.kailin.coroutines_arch.data.settings

import androidx.lifecycle.MutableLiveData
import com.kailin.coroutines_arch.data.RepoResult
import com.kailin.coroutines_arch.data.error.ErrorRespData
import com.kailin.coroutines_arch.utils.connect.toData
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import retrofit2.http.HeaderMap
import java.lang.Exception

class UserUpdateDataSourceImpl(
    private val service: UserUpdateService,
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO
) : UserUpdateDataSource {

    private val userUpdateResult = MutableLiveData<RepoResult<UserUpdateInfo>>()

    override fun observerUserUpdate() = userUpdateResult

    override suspend fun userUpdate(
        objectId: String,
        timeZone: String,
        headerMap: Map<String, String>
    ) = withContext(dispatcher) {
        try {
            userUpdateResult.postValue(RepoResult.Loading)
            val response = service.update(objectId, UserUpdateReq(timeZone), headerMap)
            userUpdateResult.postValue(
                if (response.isSuccessful && response.body() != null) {
                    RepoResult.Success(response.body()!!)
                } else if (response.errorBody() != null) {
                    val data =
                        response.errorBody()?.toData<ErrorRespData>(ErrorRespData::class.java)
                    RepoResult.Error(HttpException(response), data)
                } else {
                    RepoResult.Error(HttpException(response))
                }
            )
        } catch (e: Exception) {
            e.printStackTrace()
            userUpdateResult.postValue(RepoResult.Error(e))
        }
    }
}