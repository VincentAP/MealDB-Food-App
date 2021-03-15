package com.example.tugassoavincentardyanputra2101658344.util

import android.content.Context
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncDifferConfig
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView

interface BaseItem {
    fun layoutId(): Int
    fun id(): Long
    fun contentsTheSame(newItem: BaseItem): Boolean
}

abstract class BaseViewHolder<in T : BaseItem>(itemView: View) : RecyclerView.ViewHolder(itemView) {
    abstract fun bind(item: T, listener: (BaseItem, View) -> Unit)
    protected fun getContext(): Context = itemView.context
}

object ItemDiffCallback : DiffUtil.ItemCallback<BaseItem>() {
    override fun areItemsTheSame(oldItem: BaseItem, newItem: BaseItem)
            : Boolean = oldItem.id() == newItem.id()

    override fun areContentsTheSame(oldItem: BaseItem, newItem: BaseItem)
            : Boolean = oldItem.contentsTheSame(newItem)
}

abstract class ViewHolderFactory {
    abstract fun layoutId(): Int
    abstract fun bindView(
        item: BaseItem,
        viewHolder: BaseViewHolder<*>,
        listener: (BaseItem, View) -> Unit
    )

    abstract fun createViewHolder(parent: ViewGroup): BaseViewHolder<*>
}

class BaseRecyclerAdapter(
    private var listener: (BaseItem, View) -> Unit,
    private val viewHolderFactory: List<ViewHolderFactory> = listOf()
) : ListAdapter<BaseItem, BaseViewHolder<*>>(AsyncDifferConfig.Builder(ItemDiffCallback).build()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<*> {
        return viewHolderFactory.firstOrNull { it.layoutId() == viewType }?.createViewHolder(parent)
            ?: throw Throwable(
                "ViewHolderFactory for ${
                    parent.context.resources.getResourceEntryName(
                        viewType
                    )
                } haven't registered yet."
            )
    }

    override fun onBindViewHolder(holder: BaseViewHolder<*>, position: Int) {
        val item = getItem(position)
        viewHolderFactory.firstOrNull { it.layoutId() == item.layoutId() }
            ?.bindView(item, holder, listener)
    }

    override fun getItemViewType(position: Int): Int {
        return getItem(position).layoutId()
    }

    override fun getItemId(position: Int): Long {
        return getItem(position).id()
    }

    override fun setHasStableIds(hasStableIds: Boolean) {
        super.setHasStableIds(true)
    }

    fun setOnItemClicked(listener: (BaseItem, View) -> Unit) {
        this.listener = listener
    }
}