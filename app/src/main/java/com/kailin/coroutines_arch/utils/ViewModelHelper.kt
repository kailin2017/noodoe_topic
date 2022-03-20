package com.kailin.coroutines_arch.utils

import androidx.activity.ComponentActivity
import androidx.annotation.MainThread
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelLazy
import androidx.lifecycle.ViewModelProvider

object ViewModelHelper {

    @MainThread
    inline fun <reified VM : ViewModel> Fragment.createViewModel(
        noinline factoryProducer: (() -> ViewModelProvider.Factory)? = null
    ): Lazy<VM> {
        val factoryPromise = factoryProducer ?: {
            defaultViewModelProviderFactory
        }
        return ViewModelLazy(VM::class, { viewModelStore }, factoryPromise)
    }

    @MainThread
    inline fun <reified VM : ViewModel> ComponentActivity.createViewModel(
        noinline factoryProducer: (() -> ViewModelProvider.Factory)? = null
    ): Lazy<VM> {
        val factoryPromise = factoryProducer ?: {
            defaultViewModelProviderFactory
        }
        return ViewModelLazy(VM::class, { viewModelStore }, factoryPromise)
    }

    @JvmStatic
    fun <VM : ViewModel> createViewModel(clazz:Class<VM>, factory: (() -> ViewModelProvider.Factory)) : VM{
        return factory().create(clazz)
    }
}