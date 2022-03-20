package com.kailin.coroutines_arch.data.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.kailin.coroutines_arch.data.RepoResult
import com.kailin.coroutines_arch.data.error.ErrorRespData
import com.kailin.coroutines_arch.utils.connect.toData
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.HttpException

class TaipeiNewsDataSourceImpl(
    private val service: TaipeiNewsService,
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO
) : TaipeiNewsDataSource {

    private val observerRepo = MutableLiveData<RepoResult<TaipeiNews>>()

    override fun observerNews(): LiveData<RepoResult<TaipeiNews>> = observerRepo

    override suspend fun fetchNews() = withContext(dispatcher) {
        try {
            observerRepo.postValue(RepoResult.Loading)
            val response = service.fetchNews()
            observerRepo.postValue(
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
            observerRepo.postValue(RepoResult.Error(e))
        }
    }
}