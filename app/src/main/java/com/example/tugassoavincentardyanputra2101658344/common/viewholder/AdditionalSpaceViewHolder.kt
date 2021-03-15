package com.example.tugassoavincentardyanputra2101658344.common.viewholder

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.tugassoavincentardyanputra2101658344.R
import com.example.tugassoavincentardyanputra2101658344.databinding.AdditionalSpaceItemBinding
import com.example.tugassoavincentardyanputra2101658344.util.BaseItem
import com.example.tugassoavincentardyanputra2101658344.util.BaseViewHolder
import com.example.tugassoavincentardyanputra2101658344.util.ViewHolderFactory

class AdditionalSpaceItem : BaseItem {
    override fun layoutId(): Int = R.layout.additional_space_item
    override fun id(): Long = layoutId().hashCode().toLong()
    override fun contentsTheSame(newItem: BaseItem): Boolean =
        if (newItem is AdditionalSpaceItem) toString() == newItem.toString() else false
}

class AdditionalSpaceViewHolder(
    itemBinding: AdditionalSpaceItemBinding
) : BaseViewHolder<AdditionalSpaceItem>(itemBinding.root) {
    override fun bind(item: AdditionalSpaceItem, listener: (BaseItem, View) -> Unit) {}
}

class AdditionalSpaceViewHolderFactory : ViewHolderFactory() {
    override fun layoutId(): Int = R.layout.additional_space_item

    override fun bindView(
        item: BaseItem,
        viewHolder: BaseViewHolder<*>,
        listener: (BaseItem, View) -> Unit
    ) {
        (viewHolder as AdditionalSpaceViewHolder).bind(item as AdditionalSpaceItem, listener)
    }

    override fun createViewHolder(parent: ViewGroup): BaseViewHolder<*> {
        return AdditionalSpaceViewHolder(
            AdditionalSpaceItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }
}