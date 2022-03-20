package com.kailin.coroutines_arch.app

import androidx.lifecycle.*

abstract class BaseViewModel : ViewModel() {

    protected val _msgString = MediatorLiveData<String>()
    val msgString: LiveData<String> = _msgString

    protected val _msgRes = MediatorLiveData<Int>()
    val msgRes: LiveData<Int> = _msgRes

    protected val _isLoading = MediatorLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    fun exception(e: Throwable) {
        e.printStackTrace()
        _msgString.postValue(e.message)
    }
}
