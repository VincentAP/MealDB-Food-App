package com.example.tugassoavincentardyanputra2101658344.ui.detail.viewholder

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.tugassoavincentardyanputra2101658344.R
import com.example.tugassoavincentardyanputra2101658344.databinding.DetailListItemBinding
import com.example.tugassoavincentardyanputra2101658344.extension.ItemViewStyle
import com.example.tugassoavincentardyanputra2101658344.util.BaseItem
import com.example.tugassoavincentardyanputra2101658344.util.BaseViewHolder
import com.example.tugassoavincentardyanputra2101658344.util.ViewHolderFactory

data class DetailListItem(
    val text: String
) : BaseItem, ItemViewStyle<DetailListItem>() {
    override fun layoutId(): Int = R.layout.detail_list_item
    override fun id(): Long = text.hashCode().toLong()
    override fun contentsTheSame(newItem: BaseItem): Boolean =
        if (newItem is DetailListItem) text == newItem.text
        else false
}

class DetailListViewHolder(
    private val itemBinding: DetailListItemBinding
) : BaseViewHolder<DetailListItem>(itemBinding.root) {

    override fun bind(item: DetailListItem, listener: (BaseItem, View) -> Unit) {
        itemBinding.textDetailListItem.text = item.text
        item.applyContainerStyle(itemView)
    }
}

class DetailListViewHolderFactory : ViewHolderFactory() {
    override fun layoutId(): Int = R.layout.detail_list_item

    override fun bindView(
        item: BaseItem,
        viewHolder: BaseViewHolder<*>,
        listener: (BaseItem, View) -> Unit
    ) {
        (viewHolder as DetailListViewHolder).bind(item as DetailListItem, listener)
    }

    override fun createViewHolder(parent: ViewGroup): BaseViewHolder<*> {
        return DetailListViewHolder(
            DetailListItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }
}