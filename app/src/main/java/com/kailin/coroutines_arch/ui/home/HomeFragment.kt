package com.kailin.coroutines_arch.ui.home

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.kailin.coroutines_arch.R
import com.kailin.coroutines_arch.app.BaseFragment
import com.kailin.coroutines_arch.databinding.FragmentHomeBinding
import com.kailin.coroutines_arch.utils.ActionViewHelper
import com.kailin.coroutines_arch.widget.navigation
import com.kailin.coroutines_arch.widget.setActionBar


class HomeFragment : BaseFragment<HomeViewModel, FragmentHomeBinding>(),
    Toolbar.OnMenuItemClickListener {

    override val viewModelClass = HomeViewModel::class.java
    override val viewDataBindingClass = FragmentHomeBinding::class.java
    private val adapter: HomeAdapter by lazy {
        HomeAdapter { item, _ ->
            ActionViewHelper.actionView(requireContext(), item.url)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewDataBinding?.let {
            it.lifecycleOwner = viewLifecycleOwner
            it.viewModel = viewModel

            setActionBar(it.toolbar)
            it.toolbar.setOnMenuItemClickListener(this@HomeFragment)

            it.recyclerView.adapter = adapter
            val layoutManager = LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)
            val dividerItemDecoration = DividerItemDecoration(
                requireContext(),
                layoutManager.orientation
            )
            it.recyclerView.layoutManager = layoutManager
            it.recyclerView.addItemDecoration(dividerItemDecoration)
        }

        viewModel.fetchTaipeiNews()
        viewModel.taipeiNews.observe(viewLifecycleOwner) {
            adapter.updateData(it)
        }

        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner,
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    requireActivity().finishAndRemoveTask()
                }
            })
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.menu_home, menu);
        viewModel.timeZone.observe(viewLifecycleOwner) {
            menu.findItem(R.id.menu_setting).title = it
        }
    }

    override fun onMenuItemClick(item: MenuItem): Boolean {
        if (item.itemId == R.id.menu_setting) {
            navigation(HomeFragmentDirections.actionHomeFragmentToSettingsFragment())
            return true
        }
        return false
    }
}