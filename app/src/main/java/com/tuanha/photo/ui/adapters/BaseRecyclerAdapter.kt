package com.tuanha.photo.ui.adapters

import android.content.Context
import android.view.View
import android.view.ViewGroup
import androidx.annotation.CallSuper
import androidx.recyclerview.widget.*
import java.util.concurrent.Executors

abstract class BaseRecyclerAdapter<T>(callBack: DiffUtil.ItemCallback<T>) : ListAdapter<T, BaseViewHolder>(
    AsyncDifferConfig.Builder<T>(callBack)
        .setBackgroundThreadExecutor(Executors.newSingleThreadExecutor())
        .build()
) {

    open fun setRecyclerView(recyclerView: RecyclerView) {
        recyclerView.adapter = this
        recyclerView.layoutManager = getLayoutManager(recyclerView.context)
    }

    open fun getLayoutManager(context: Context): RecyclerView.LayoutManager {
        return LinearLayoutManager(context)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder {
        return BaseViewHolder(createView(parent = parent, viewType = viewType), viewType)
    }

    @Suppress("UNCHECKED_CAST")
    override fun onBindViewHolder(holder: BaseViewHolder, position: Int, payloads: MutableList<Any>) {
        val payload = payloads.getOrNull(0) as? MutableList<Any>

        if (!payload.isNullOrEmpty()) {
            bind(holder.itemView, holder.viewType, position, getItem(position), payload)
        } else {
            super.onBindViewHolder(holder, position, payloads)
        }
    }

    override fun onBindViewHolder(holder: BaseViewHolder, position: Int) {
        bind(holder.itemView, holder.viewType, position, getItem(position))
    }

    protected abstract fun createView(parent: ViewGroup, viewType: Int? = 0): View

    @CallSuper
    protected open fun bind(view: View, viewType: Int, position: Int, item: T, payloads: MutableList<Any>) {
        view.tag = item
    }

    @CallSuper
    protected open fun bind(view: View, viewType: Int, position: Int, item: T) {
        view.tag = item
    }
}

class BaseViewHolder(val view: View, val viewType: Int) : RecyclerView.ViewHolder(view)