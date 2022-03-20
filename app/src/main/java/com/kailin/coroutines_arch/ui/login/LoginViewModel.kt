package com.kailin.coroutines_arch.ui.login

import androidx.lifecycle.*
import com.kailin.coroutines_arch.BuildConfig
import com.kailin.coroutines_arch.R
import com.kailin.coroutines_arch.app.BaseViewModel
import com.kailin.coroutines_arch.data.RepoResult
import com.kailin.coroutines_arch.data.login.*
import com.kailin.coroutines_arch.utils.connect.ConnectHelper
import com.kailin.coroutines_arch.utils.isEmail
import kotlinx.coroutines.launch

class LoginViewModel : BaseViewModel() {

    private val loginRepository: LoginRepository by lazy {
        val service = ConnectHelper.createService(LoginService::class.java)
        val dataSource = LoginDataSourceImpl(service)
        LoginRepositoryImpl(dataSource)
    }

    private val loginResult = loginRepository.observerLogin()

    val username = MutableLiveData<String>()

    private val _usernameError = MutableLiveData<Int>()
    val usernameError: LiveData<Int> = _usernameError

    val password = MutableLiveData<String>()

    private val _passwordError = MutableLiveData<Int>()
    val passwordError: LiveData<Int> = _passwordError

    private val _displayedPassword = MutableLiveData(false)
    val displayedPassword: LiveData<Boolean> = _displayedPassword

    val loginFinish: LiveData<UserInfo> =
        loginResult.distinctUntilChanged().switchMap { filterState(it) }

    private val _isShowDevLogin = MutableLiveData(BuildConfig.DEV_IS_SHOW_DEVLOGIN)
    val isShowDevLogin: LiveData<Boolean> = _isShowDevLogin

    init {
        _isLoading.addSource(loginResult.map { it is RepoResult.Loading }) {
            _isLoading.value = it
        }
    }

    fun devLogin() {
        username.value = "test2@qq.com"
        password.value = "test1234qq"
        login()
    }

    fun login() {
        val username = username.value
        val password = password.value

        if (!loginInputValid(username, password)) {
            return
        }

        viewModelScope.launch {
            loginRepository.login(username!!, password!!)
        }
    }

    fun switchPasswordDisplayed() {
        _displayedPassword.value = !_displayedPassword.value!!
    }

    private fun loginInputValid(username: String?, password: String?): Boolean {
        var usernameIsValid = false
        _usernameError.value = when {
            username.isNullOrEmpty() -> {
                R.string.login_username_error_empty
            }
            !username.isEmail() -> {
                R.string.login_username_error_invalid
            }
            else -> {
                usernameIsValid = true
                R.string.inputText_blank
            }
        }

        var passwordIsValid = false
        _passwordError.value = when {
            password.isNullOrEmpty() -> {
                R.string.login_password_error_empty
            }
            password.length < 6 -> {
                R.string.login_password_error_length
            }
            else -> {
                passwordIsValid = true
                R.string.inputText_blank
            }
        }

        return usernameIsValid && passwordIsValid
    }

    private fun filterState(result: RepoResult<UserInfo>): LiveData<UserInfo> {
        val liveData = MutableLiveData<UserInfo>()
        when (result) {
            is RepoResult.Success -> {
                liveData.value = result.data!!
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