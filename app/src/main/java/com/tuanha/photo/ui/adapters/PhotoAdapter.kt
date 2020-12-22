package com.tuanha.photo.ui.adapters

import android.annotation.SuppressLint
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.tuanha.photo.R
import com.tuanha.photo.entities.Photo
import java.util.*

class PhotoAdapter (
    private val onItemClickListener: (View, PhotoViewItem) -> Unit = { _, _ -> }
) : BaseRecyclerAdapter<PhotoViewItem>(object : DiffUtil.ItemCallback<PhotoViewItem>() {

    override fun areItemsTheSame(oldItem: PhotoViewItem, newItem: PhotoViewItem): Boolean {
        return oldItem.agent.agentId == newItem.agent.agentId
    }

    override fun areContentsTheSame(oldItem: PhotoViewItem, newItem: PhotoViewItem): Boolean {
        return oldItem.agent.ipAddress == newItem.agent.ipAddress
                && oldItem.status == newItem.status
                && oldItem.syncStr == newItem.syncStr
                && oldItem.syncing == newItem.syncing
                && oldItem.errorStr == newItem.errorStr
    }

    override fun getChangePayload(oldItem: PhotoViewItem, newItem: PhotoViewItem): Any? {
        val payloads = ArrayList<Any>()

        if (oldItem.syncStr != newItem.syncStr) {
            payloads.add(PAYLOAD_SYNC)
        }

        if (oldItem.errorStr != newItem.errorStr) {
            payloads.add(PAYLOAD_ERROR)
        }

        if (oldItem.status != newItem.status) {
            payloads.add(PAYLOAD_STATUS)
        }

        if (oldItem.syncing != newItem.syncing) {
            payloads.add(PAYLOAD_SYNCING)
        }

        payloads.add(Int.MAX_VALUE)

        return if (payloads.size > 0) {
            payloads
        } else {
            super.getChangePayload(oldItem, newItem)
        }
    }

}) {

    companion object {
        const val PAYLOAD_SYNC = "PAYLOAD_SYNC"
        const val PAYLOAD_ERROR = "PAYLOAD_ERROR"
        const val PAYLOAD_STATUS = "PAYLOAD_STATUS"
        const val PAYLOAD_SYNCING = "PAYLOAD_SYNCING"
    }

    override fun setRecyclerView(recyclerView: RecyclerView) {
        super.setRecyclerView(recyclerView)
        recyclerView.clipToPadding = false
    }

    override fun createView(parent: ViewGroup, viewType: Int?): View {
        val view = parent.inflate(R.layout.item_agent)

        view.setOnClickListener { v ->
            (view.tag as? PhotoViewItem)?.takeIf { it.errorStr.isNotBlank() }?.let { onItemClickListener.invoke(v, it) }
        }

        return view
    }

    override fun bind(view: View, viewType: Int, position: Int, item: PhotoViewItem, payloads: MutableList<Any>) {
        view.tag = item

        refreshBackground(view, position)

        if (payloads.contains(PAYLOAD_SYNC)) {
            refreshSync(view, item)
        }

        if (payloads.contains(PAYLOAD_ERROR)) {
            refreshError(view, item)
        }

        if (payloads.contains(PAYLOAD_STATUS)) {
            refreshStatus(view, item)
        }

        if (payloads.contains(PAYLOAD_SYNCING)) {
            refreshSyncing(view, item)
        }
    }

    override fun bind(view: View, viewType: Int, position: Int, item: PhotoViewItem) {
        view.tag = item

        refreshBackground(view, position)

        view.tvName.text = item.agent.ipAddress.takeIf { it.isNotBlank() } ?: "MAIN"
        view.tvNameSub.text = item.agent.agentId.replace("AgentHost-", "").takeIf { it != Agent.ID_MAIN } ?: ""

        refreshSync(view, item)
        refreshError(view, item)
        refreshStatus(view, item)
        refreshSyncing(view, item)
    }

    @SuppressLint("SetTextI18n")
    private fun refreshSync(view: View, item: PhotoViewItem) {
        view.tvSync.text = item.syncStr
    }

    private fun refreshError(view: View, item: PhotoViewItem) {
        view.tvError.setVisible(!item.errorStr.isBlank())
    }

    private fun refreshStatus(view: View, item: PhotoViewItem) {
        view.tvConnect.setVisible(item.status == Agent.AgentStatus.CONNECT)
        view.tvDisconnect.setVisible(item.status == Agent.AgentStatus.DISCONNECT)
        view.tvConnectLost.setVisible(item.status == Agent.AgentStatus.CONNECT_LOST)
        view.tvRequestConnect.setVisible(item.status == Agent.AgentStatus.REQUEST_CONNECT)
    }

    @SuppressLint("SetTextI18n")
    private fun refreshSyncing(view: View, item: PhotoViewItem) {
        view.tvSyncing.setVisible(item.syncing)
    }

}

data class PhotoViewItem(val photo: Photo){

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