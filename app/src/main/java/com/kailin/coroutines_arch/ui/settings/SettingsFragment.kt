package com.kailin.coroutines_arch.ui.settings

import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import com.kailin.coroutines_arch.R
import com.kailin.coroutines_arch.app.BaseFragment
import com.kailin.coroutines_arch.databinding.FragmentSettingsBinding
import com.kailin.coroutines_arch.widget.navigationPop
import com.kailin.coroutines_arch.widget.setActionBar


class SettingsFragment : BaseFragment<SettingsViewModel, FragmentSettingsBinding>(),
    AdapterView.OnItemSelectedListener {

    override val viewModelClass = SettingsViewModel::class.java
    override val viewDataBindingClass = FragmentSettingsBinding::class.java

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewDataBinding?.let {
            it.viewModel = viewModel
            it.lifecycleOwner = viewLifecycleOwner
            setActionBar(it.toolbar)
            it.toolbar.setNavigationOnClickListener {
                navigationPop()
            }
        }

        viewModel.fetchTimeZone()
        viewModel.timeZone.observe(viewLifecycleOwner) {
            viewDataBinding?.timeZone?.adapter = ArrayAdapter(
                requireContext(),
                R.layout.support_simple_spinner_dropdown_item,
                it
            )
            viewModel.refreshSelected()
        }

        viewModel.spinnerSelected.observe(viewLifecycleOwner) {
            try {
                viewDataBinding?.timeZone?.setSelection(it, true)
                viewDataBinding?.timeZone?.onItemSelectedListener = this@SettingsFragment
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    override fun onItemSelected(
        adapterView: AdapterView<*>?,
        view: View?,
        position: Int,
        id: Long
    ) {
        viewModel.setTimeZone(position)
    }

    override fun onNothingSelected(adapterView: AdapterView<*>?) {
    }
}