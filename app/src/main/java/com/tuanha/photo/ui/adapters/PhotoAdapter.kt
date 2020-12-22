package com.tuanha.photo.ui.adapters

import android.content.Context
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.tuanha.photo.App
import com.tuanha.photo.R
import com.tuanha.photo.entities.Photo
import com.tuanha.photo.utils.*
import kotlinx.android.synthetic.main.item_photo.view.*
import java.util.*
import kotlin.math.roundToInt

class PhotoAdapter(
    private val onItemClickListener: (View, PhotoViewItem) -> Unit = { _, _ -> }
) : BaseRecyclerAdapter<PhotoViewItem>(object : DiffUtil.ItemCallback<PhotoViewItem>() {

    override fun areItemsTheSame(oldItem: PhotoViewItem, newItem: PhotoViewItem): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: PhotoViewItem, newItem: PhotoViewItem): Boolean {
        return oldItem.photo.url == newItem.photo.url
    }

    override fun getChangePayload(oldItem: PhotoViewItem, newItem: PhotoViewItem): Any? {
        val payloads = ArrayList<Any>()

        payloads.add(Int.MAX_VALUE)

        return if (payloads.size > 0) {
            payloads
        } else {
            super.getChangePayload(oldItem, newItem)
        }
    }

}) {

    override fun setRecyclerView(recyclerView: RecyclerView) {
        super.setRecyclerView(recyclerView)
        recyclerView.clipToPadding = false
    }

    override fun getLayoutManager(context: Context): RecyclerView.LayoutManager {
        return GridLayoutManager(context, COL)
    }

    override fun createView(parent: ViewGroup, viewType: Int?): View {
        val view = parent.inflate(R.layout.item_photo)

        view.resize(ITEM_WIDTH, ITEM_WIDTH)

        (view.layoutParams as ViewGroup.MarginLayoutParams).leftMargin = ITEM_PADDING
        (view.layoutParams as ViewGroup.MarginLayoutParams).topMargin = ITEM_PADDING
        (view.layoutParams as ViewGroup.MarginLayoutParams).rightMargin = ITEM_PADDING
        (view.layoutParams as ViewGroup.MarginLayoutParams).bottomMargin = ITEM_PADDING

        view.setOnClickListener { v -> }

        return view
    }

    override fun bind(view: View, viewType: Int, position: Int, item: PhotoViewItem) {
        super.bind(view, viewType, position, item)
        view.ivPhoto.load(item.photo.url)
    }

    companion object {

        private var WIDTH_SCREEN = getScreenWidth(App.shared) - 3 * 8.toPx()

        private var COL = (WIDTH_SCREEN / 80.toPx() * 1f).roundToInt().coerceAtLeast(4)

        private var ITEM_WIDTH = WIDTH_SCREEN / COL
        private var ITEM_PADDING = 4.toPx()

    }
}

data class PhotoViewItem(val photo: Photo) {

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as PhotoViewItem

        if (photo != other.photo) return false

        return true
    }

    override fun hashCode(): Int {
        return photo.hashCode()
    }
}