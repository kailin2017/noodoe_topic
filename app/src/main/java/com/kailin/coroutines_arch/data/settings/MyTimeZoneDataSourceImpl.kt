package com.kailin.coroutines_arch.data.settings

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.kailin.coroutines_arch.data.RepoResult
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class MyTimeZoneDataSourceImpl(
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO
) : MyTimeZoneDataSource {

    private val myTimeZoneResult = MutableLiveData<RepoResult<List<MyTimeZone>>>()

    override fun observerTimeZone(): LiveData<RepoResult<List<MyTimeZone>>> {
        return myTimeZoneResult
    }

    override suspend fun fetchTimeZone() = withContext(dispatcher) {
        val timeZoneList = listOf(
            MyTimeZone("GMT-1"),
            MyTimeZone("GMT-2"),
            MyTimeZone("GMT-3"),
            MyTimeZone("GMT-4"),
            MyTimeZone("GMT-5"),
            MyTimeZone("GMT-6"),
            MyTimeZone("GMT-7"),
            MyTimeZone("GMT-8"),
            MyTimeZone("GMT-9"),
            MyTimeZone("GMT-10"),
            MyTimeZone("GMT-11"),
            MyTimeZone("GMT-12"),
            MyTimeZone("GMT+0"),
            MyTimeZone("GMT+1"),
            MyTimeZone("GMT+2"),
            MyTimeZone("GMT+3"),
            MyTimeZone("GMT+4"),
            MyTimeZone("GMT+5"),
            MyTimeZone("GMT+6"),
            MyTimeZone("GMT+7"),
            MyTimeZone("GMT+8"),
            MyTimeZone("GMT+9"),
            MyTimeZone("GMT+10"),
            MyTimeZone("GMT+11"),
            MyTimeZone("GMT+12"),
            MyTimeZone("GMT+13"),
            MyTimeZone("GMT+14"),
        )
        val repoResult = RepoResult.Success(timeZoneList)
        myTimeZoneResult.postValue(repoResult)
    }
}