package com.example.tugassoavincentardyanputra2101658344.ui.detail.viewholder

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.tugassoavincentardyanputra2101658344.R
import com.example.tugassoavincentardyanputra2101658344.databinding.DetailImageItemBinding
import com.example.tugassoavincentardyanputra2101658344.extension.loadImage
import com.example.tugassoavincentardyanputra2101658344.util.BaseItem
import com.example.tugassoavincentardyanputra2101658344.util.BaseViewHolder
import com.example.tugassoavincentardyanputra2101658344.util.ViewHolderFactory

data class DetailImageItem(
    val imageUrl: String
) : BaseItem {
    override fun layoutId(): Int = R.layout.detail_image_item
    override fun id(): Long = imageUrl.hashCode().toLong()
    override fun contentsTheSame(newItem: BaseItem): Boolean = if (newItem is DetailImageItem) {
        imageUrl == newItem.imageUrl
    } else false
}

class DetailImageViewHolder(
    private val itemBinding: DetailImageItemBinding
) : BaseViewHolder<DetailImageItem>(itemBinding.root) {

    override fun bind(item: DetailImageItem, listener: (BaseItem, View) -> Unit) {
        itemBinding.imageFood.loadImage(item.imageUrl)
    }
}

class DetailImageViewHolderFactory : ViewHolderFactory() {
    override fun layoutId(): Int = R.layout.detail_image_item

    override fun bindView(
        item: BaseItem,
        viewHolder: BaseViewHolder<*>,
        listener: (BaseItem, View) -> Unit
    ) {
        (viewHolder as DetailImageViewHolder).bind(item as DetailImageItem, listener)
    }

    override fun createViewHolder(parent: ViewGroup): BaseViewHolder<*> {
        return DetailImageViewHolder(
            DetailImageItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }
}