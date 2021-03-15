package com.example.tugassoavincentardyanputra2101658344.common.viewholder

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.tugassoavincentardyanputra2101658344.R
import com.example.tugassoavincentardyanputra2101658344.databinding.NoDataItemBinding
import com.example.tugassoavincentardyanputra2101658344.util.BaseItem
import com.example.tugassoavincentardyanputra2101658344.util.BaseViewHolder
import com.example.tugassoavincentardyanputra2101658344.util.ViewHolderFactory

class NoDataItem : BaseItem {
    override fun layoutId(): Int = R.layout.no_data_item
    override fun id(): Long = layoutId().hashCode().toLong()
    override fun contentsTheSame(newItem: BaseItem): Boolean = if (newItem is NoDataItem)
        toString() == newItem.toString() else false
}

class NoDataViewHolder(
    itemBinding: NoDataItemBinding
) : BaseViewHolder<NoDataItem>(itemBinding.root) {
    override fun bind(item: NoDataItem, listener: (BaseItem, View) -> Unit) {}
}

class NoDataViewHolderFactory : ViewHolderFactory() {
    override fun layoutId(): Int = R.layout.no_data_item

    override fun bindView(
        item: BaseItem,
        viewHolder: BaseViewHolder<*>,
        listener: (BaseItem, View) -> Unit
    ) {
        (viewHolder as NoDataViewHolder).bind(item as NoDataItem, listener)
    }

    override fun createViewHolder(parent: ViewGroup): BaseViewHolder<*> {
        return NoDataViewHolder(
            NoDataItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }
}