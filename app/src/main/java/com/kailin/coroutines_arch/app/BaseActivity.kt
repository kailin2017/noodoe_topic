package com.kailin.coroutines_arch.app

import android.os.Bundle
import android.view.LayoutInflater
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.allViews
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import com.kailin.coroutines_arch.utils.ViewModelHelper.createViewModel
import com.kailin.coroutines_arch.widget.DialogHelper
import java.lang.reflect.Method

abstract class BaseActivity<M : BaseViewModel, V : ViewDataBinding> : AppCompatActivity() {

    protected var viewDataBinding: V? = null
    protected val viewModel: M by lazy { createViewModel(viewModelClass) { defaultViewModelProviderFactory } }
    protected abstract val viewModelClass: Class<M>
    protected abstract val viewDataBindingClass: Class<V>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val method: Method = viewDataBindingClass.getMethod("inflate", LayoutInflater::class.java)
        viewDataBinding = method.invoke(null, layoutInflater) as V
        setContentView(viewDataBinding?.root)
        with(viewModel) {
            msgString.observe(this@BaseActivity) {
                DialogHelper.msgDialog(this@BaseActivity, msg = it)
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        viewDataBinding ?: return
        viewDataBinding?.root?.allViews?.iterator()?.forEachRemaining {
            if (it is RecyclerView) {
                it.adapter = null
            }
        }
        viewDataBinding = null
    }
}