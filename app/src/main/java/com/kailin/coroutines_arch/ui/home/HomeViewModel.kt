package com.kailin.coroutines_arch.ui.home

import androidx.lifecycle.*
import com.kailin.coroutines_arch.app.BaseViewModel
import com.kailin.coroutines_arch.data.RepoResult
import com.kailin.coroutines_arch.data.home.*
import com.kailin.coroutines_arch.utils.connect.ConnectHelper
import kotlinx.coroutines.launch

class HomeViewModel : BaseViewModel() {

    private val homeRepository: HomeRepository by lazy {
        val service = ConnectHelper.createService(TaipeiNewsService::class.java)
        val dataSource = TaipeiNewsDataSourceImpl(service)
        HomeRepositoryImpl(dataSource)
    }

    val timeZone: LiveData<String> = homeRepository.userInfo().map { it?.timeZone }

    val taipeiNews: LiveData<List<TaipeiNewsItem>> =
        homeRepository.observerNews().switchMap { filterTaipeiNews(it) }

    private val _isEmpty = MutableLiveData(false)

    val isEmpty: LiveData<Boolean> = _isEmpty

    init {
        _isLoading.addSource(homeRepository.observerNews().map { it is RepoResult.Loading }) {
            _isLoading.value = it
        }
    }

    fun fetchTaipeiNews() {
        viewModelScope.launch {
            homeRepository.fetchNews()
        }
    }

    private fun filterTaipeiNews(result: RepoResult<TaipeiNews>): LiveData<List<TaipeiNewsItem>> {
        val liveData = MutableLiveData<List<TaipeiNewsItem>>()
        when (result) {
            is RepoResult.Success -> {
                val data: TaipeiNews = result.data
                if (data.News.isNullOrEmpty()) {
                    _isEmpty.value = true
                } else {
                    liveData.value = data.News.filter { it.isValid(data.updateTime) }
                }
            }
            is RepoResult.Error -> {
                _msgString.value = if (result.data != null) {
                    result.data?.error
                } else {
                    result.exception.message
                }
            }
        }
        return liveData
    }
}