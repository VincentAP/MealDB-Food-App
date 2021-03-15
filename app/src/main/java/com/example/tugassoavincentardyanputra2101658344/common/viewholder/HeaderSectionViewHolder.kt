package com.example.tugassoavincentardyanputra2101658344.common.viewholder

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.tugassoavincentardyanputra2101658344.R
import com.example.tugassoavincentardyanputra2101658344.databinding.HeaderSectionItemBinding
import com.example.tugassoavincentardyanputra2101658344.extension.ItemViewStyle
import com.example.tugassoavincentardyanputra2101658344.util.BaseItem
import com.example.tugassoavincentardyanputra2101658344.util.BaseViewHolder
import com.example.tugassoavincentardyanputra2101658344.util.ViewHolderFactory

data class HeaderSectionItem(
    val title: String,
    val info: String? = null
) : BaseItem, ItemViewStyle<HeaderSectionItem>() {
    override fun layoutId(): Int = R.layout.header_section_item
    override fun id(): Long = title.hashCode().toLong()
    override fun contentsTheSame(newItem: BaseItem): Boolean = if (newItem is HeaderSectionItem) {
        toString() == newItem.toString()
    } else false
}

class HeaderSectionViewHolder(
    private val itemBinding: HeaderSectionItemBinding
) : BaseViewHolder<HeaderSectionItem>(itemBinding.root) {

    override fun bind(item: HeaderSectionItem, listener: (BaseItem, View) -> Unit) {
        itemBinding.apply {
            textHeaderTitle.apply {
                text = item.title
                item.applyTextStyle(this)
            }
            item.info?.let {
                textHeaderInfo.apply {
                    visibility = View.VISIBLE
                    text = it
                }
            }
        }

        item.applyContainerStyle(itemView)
    }
}

class HeaderSectionViewHolderFactory : ViewHolderFactory() {
    override fun layoutId(): Int = R.layout.header_section_item

    override fun bindView(
        item: BaseItem,
        viewHolder: BaseViewHolder<*>,
        listener: (BaseItem, View) -> Unit
    ) {
        (viewHolder as HeaderSectionViewHolder).bind(item as HeaderSectionItem, listener)
    }

    override fun createViewHolder(parent: ViewGroup): BaseViewHolder<*> {
        return HeaderSectionViewHolder(
            HeaderSectionItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }
}