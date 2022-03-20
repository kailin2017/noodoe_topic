package com.kailin.coroutines_arch.ui.settings

import androidx.lifecycle.*
import com.kailin.coroutines_arch.app.BaseViewModel
import com.kailin.coroutines_arch.data.*
import com.kailin.coroutines_arch.data.settings.*
import com.kailin.coroutines_arch.utils.connect.ConnectHelper
import kotlinx.coroutines.launch

class SettingsViewModel : BaseViewModel() {

    private val settingsRepository: SettingsRepository by lazy {
        val myTimeZoneDataSource = MyTimeZoneDataSourceImpl()
        val userUpdateDataSource =
            UserUpdateDataSourceImpl(ConnectHelper.createService(UserUpdateService::class.java))
        SettingsRepositoryImpl(myTimeZoneDataSource, userUpdateDataSource)
    }

    private val userInfo = settingsRepository.userInfo()
    val mail: LiveData<String> = userInfo.map { it.reportEmail }

    private val timeZoneResult = settingsRepository.observerTimeZone()

    val timeZone: LiveData<List<String>> =
        timeZoneResult.distinctUntilChanged().switchMap { spinnerTimeZoneStateFilter(it) }

    private val _spinnerSelected = MutableLiveData<Int>()
    val spinnerSelected: LiveData<Int> = _spinnerSelected

    fun refreshSelected(){
        _spinnerSelected.value = timeZone.value?.indexOf(userInfo.value?.timeZone)
    }

    fun fetchTimeZone() {
        viewModelScope.launch {
            settingsRepository.fetchTimeZone()
        }
    }

    fun setTimeZone(position: Int) {
        viewModelScope.launch {
            settingsRepository.setTimeZone(position)
        }
    }

    private fun spinnerTimeZoneStateFilter(repoResult: RepoResult<List<MyTimeZone>>): LiveData<List<String>> {
        val liveData = MutableLiveData<List<String>>()
        if (repoResult.succeeded) {
            liveData.value = (repoResult as RepoResult.Success<List<MyTimeZone>>).data.map { it.timeZone }
        }
        return liveData
    }
}