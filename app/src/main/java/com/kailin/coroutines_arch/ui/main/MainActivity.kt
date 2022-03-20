package com.kailin.coroutines_arch.ui.main

import com.kailin.coroutines_arch.app.BaseActivity
import com.kailin.coroutines_arch.databinding.ActivityMainBinding

class MainActivity : BaseActivity<MainViewModel, ActivityMainBinding>() {

    override val viewModelClass = MainViewModel::class.java
    override val viewDataBindingClass = ActivityMainBinding::class.java
}