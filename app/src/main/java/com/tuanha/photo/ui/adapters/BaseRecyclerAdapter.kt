package com.tuanha.photo.ui.adapters

import android.content.Context
import android.view.View
import android.view.ViewGroup
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

    override fun onBindViewHolder(holder: BaseViewHolder, position: Int, payloads: MutableList<Any>) {
        val payload = payloads.getOrNull(0) as List<Any>

        if (!payload.isNullOrEmpty()) {
            bind(holder.itemView, holder.viewType, position, getItem(position), payload.toMutableList())
        } else {
            super.onBindViewHolder(holder, position, payloads)
        }
    }

    override fun onBindViewHolder(holder: BaseViewHolder, position: Int) {
        bind(holder.itemView, holder.viewType, position, getItem(position))
    }

    protected abstract fun createView(parent: ViewGroup, viewType: Int? = 0): View

    protected open fun bind(view: View, viewType: Int, position: Int, item: T, payloads: MutableList<Any>) {}

    protected abstract fun bind(view: View, viewType: Int, position: Int, item: T)
}

class BaseViewHolder(val view: View, val viewType: Int) : RecyclerView.ViewHolder(view)