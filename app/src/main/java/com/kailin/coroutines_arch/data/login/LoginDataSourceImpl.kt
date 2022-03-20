package com.kailin.coroutines_arch.data.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.kailin.coroutines_arch.data.RepoResult
import com.kailin.coroutines_arch.data.UserInfoDataSource
import com.kailin.coroutines_arch.data.error.ErrorRespData
import com.kailin.coroutines_arch.utils.connect.toData
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.HttpException

class LoginDataSourceImpl(
    private val loginService: LoginService,
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO
) : LoginDataSource {

    private val loginRespData = MutableLiveData<RepoResult<UserInfo>>()

    override fun observerLogin(): LiveData<RepoResult<UserInfo>> = loginRespData

    override suspend fun login(username: String, password: String) = withContext(dispatcher) {
        try {
            loginRespData.postValue(RepoResult.Loading)
            val response = loginService.login(LoginReq(username, password), UserInfoDataSource.getHeaderMap())
            loginRespData.postValue(
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
            loginRespData.postValue(RepoResult.Error(e))
        }
    }
}