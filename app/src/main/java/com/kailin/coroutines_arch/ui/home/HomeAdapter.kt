package com.kailin.coroutines_arch.ui.home

import com.kailin.coroutines_arch.data.home.TaipeiNewsItem
import com.kailin.coroutines_arch.databinding.ItemHomeBinding
import com.kailin.coroutines_arch.widget.recyclerView.MyDataAdapter
import com.kailin.coroutines_arch.widget.recyclerView.ViewHolder

class HomeAdapter(callback: (TaipeiNewsItem, Int) -> Unit) :
    MyDataAdapter<ItemHomeBinding, TaipeiNewsItem>(callback) {

    override val viewDataBindingClass: Class<ItemHomeBinding>
        get() = ItemHomeBinding::class.java

    override fun onBindViewHolder(holder: ViewHolder<ItemHomeBinding>, position: Int) {
        super.onBindViewHolder(holder, position)
        holder.binding.newsItem = data[position]
    }
}