package com.kailin.coroutines_arch.app

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat
import androidx.core.view.allViews
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.kailin.coroutines_arch.utils.ViewModelHelper.createViewModel
import com.kailin.coroutines_arch.widget.DialogHelper
import com.kailin.coroutines_arch.widget.MessageUtils
import java.lang.reflect.Method

abstract class BaseFragment<M : BaseViewModel, V : ViewDataBinding> : Fragment() {

    protected var viewDataBinding: V? = null
    protected val viewModel: M by lazy { createViewModel(viewModelClass) { defaultViewModelProviderFactory } }
    protected abstract val viewModelClass: Class<M>
    protected abstract val viewDataBindingClass: Class<V>
    protected lateinit var messageUtils: MessageUtils
    protected val TAG = javaClass.canonicalName

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        messageUtils = MessageUtils(requireContext())
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val method: Method = viewDataBindingClass.getMethod("inflate", LayoutInflater::class.java)
        viewDataBinding = method.invoke(null, layoutInflater) as V
        return viewDataBinding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(viewModel) {
            msgString.observe(viewLifecycleOwner) {
                it ?: return@observe
                DialogHelper.msgDialog(requireContext(), msg = it)
            }
            msgRes.observe(viewLifecycleOwner) {
                DialogHelper.msgDialog(requireContext(), msg = getString(it))
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        viewDataBinding ?: return
        viewDataBinding?.root?.allViews?.iterator()?.forEachRemaining {
            if (it is RecyclerView) {
                it.adapter = null
            }
        }
        viewDataBinding = null
    }
}