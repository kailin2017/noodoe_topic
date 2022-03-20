package com.kailin.coroutines_arch.widget.recyclerView

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.kailin.coroutines_arch.R
import java.lang.reflect.Method

abstract class MyDataAdapter<V : ViewDataBinding, D>(private val callback: ((D, Int) -> Unit)? = null) :
    RecyclerView.Adapter<ViewHolder<V>>() {

    protected abstract val viewDataBindingClass: Class<V>
    protected val data: ArrayList<D> = arrayListOf()
    private val itemClick = View.OnClickListener {
        try {
            val position: Int = it.getTag(R.id.tag_MyDataAdapterItemPosition) as Int
            callback?.invoke(data[position], position)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder<V> {
        val method: Method = viewDataBindingClass.getMethod(
            "inflate",
            LayoutInflater::class.java,
            ViewGroup::class.java,
            Boolean::class.java
        )
        val viewDataBinding =
            method.invoke(null, LayoutInflater.from(parent.context), parent, false) as V
        return ViewHolder(viewDataBinding).also {
            callback ?: return@also
            it.itemView.setOnClickListener(itemClick)
        }
    }

    override fun onBindViewHolder(holder: ViewHolder<V>, position: Int) {
        callback ?: return
        holder.itemView.setTag(R.id.tag_MyDataAdapterItemPosition, position)
    }

    override fun getItemCount() = data.size

    fun updateData(newData: List<D>) {
        val result = DiffUtil.calculateDiff(MyDataDiffCallBack(data, newData))
        data.clear()
        data.addAll(newData)
        result.dispatchUpdatesTo(this@MyDataAdapter)
    }

    fun appendData(newData: List<D>) {
        val result = DiffUtil.calculateDiff(MyDataDiffCallBack(data, newData))
        data.addAll(newData)
        result.dispatchUpdatesTo(this@MyDataAdapter)
    }

    @SuppressLint("NotifyDataSetChanged")
    fun clearData() {
        data.clear()
        notifyDataSetChanged()
    }
}

class ViewHolder<V : ViewDataBinding>(val binding: V) : RecyclerView.ViewHolder(binding.root)