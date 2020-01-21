package com.hsnozan.basemvvm.core

import android.view.View
import android.view.ViewGroup
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import com.hsnozan.basemvvm.R

abstract class BaseAdapter<T, V : ViewDataBinding> : RecyclerView.Adapter<BaseViewHolder<V>>() {

    protected lateinit var items: List<T>

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<V> {
        var binding: V = createBinding(parent)
        return BaseViewHolder(binding)
    }

    abstract fun createBinding(parent: ViewGroup): V

    override fun onBindViewHolder(holder: BaseViewHolder<V>, position: Int) {
        holder.binding.root.setTag(R.string.position, position)
        bind(holder.binding, items[position])
    }

    abstract fun bind(binding: V, item: T?)

    override fun getItemCount(): Int {
        return items.size
    }

    abstract fun setData(items: List<T>)

    fun getPositionBaseOnTag(theTag: String?): Int {
        val length = items.size
        for (i in 0..length) {
            if ((items[i] as View).tag == theTag) {
                return i
            }
        }

        return -1
    }

}