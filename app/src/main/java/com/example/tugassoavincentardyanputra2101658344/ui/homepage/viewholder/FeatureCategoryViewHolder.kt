package com.example.tugassoavincentardyanputra2101658344.ui.homepage.viewholder

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.tugassoavincentardyanputra2101658344.R
import com.example.tugassoavincentardyanputra2101658344.databinding.FeatureCategoryItemBinding
import com.example.tugassoavincentardyanputra2101658344.extension.ItemViewStyle
import com.example.tugassoavincentardyanputra2101658344.extension.loadImage
import com.example.tugassoavincentardyanputra2101658344.util.BaseItem
import com.example.tugassoavincentardyanputra2101658344.util.BaseViewHolder
import com.example.tugassoavincentardyanputra2101658344.util.ViewHolderFactory

data class FeatureCategoryItem(
    val imageUrl: String,
    val title: String,
    val isCategory: Boolean = true
) : BaseItem, ItemViewStyle<FeatureCategoryItem>() {
    override fun layoutId(): Int = R.layout.feature_category_item
    override fun id(): Long = imageUrl.hashCode().toLong()
    override fun contentsTheSame(newItem: BaseItem): Boolean = if (newItem is FeatureCategoryItem) {
        toString() == newItem.toString()
    } else false
}

class FeatureCategoryViewHolder(
    private val itemBinding: FeatureCategoryItemBinding
) : BaseViewHolder<FeatureCategoryItem>(itemBinding.root) {

    override fun bind(item: FeatureCategoryItem, listener: (BaseItem, View) -> Unit) {
        itemBinding.apply {
            imageFood.loadImage(item.imageUrl)
            textItem.text = item.title
            itemBinding.cardFeatureCategoryItemWrapper.setOnClickListener {
                listener(item, it)
            }
        }
        item.applyContainerStyle(itemView)
    }
}

class FeatureCategoryViewHolderFactory : ViewHolderFactory() {
    override fun layoutId(): Int = R.layout.feature_category_item

    override fun bindView(
        item: BaseItem,
        viewHolder: BaseViewHolder<*>,
        listener: (BaseItem, View) -> Unit
    ) {
        (viewHolder as FeatureCategoryViewHolder).bind(item as FeatureCategoryItem, listener)
    }

    override fun createViewHolder(parent: ViewGroup): BaseViewHolder<*> {
        return FeatureCategoryViewHolder(
            FeatureCategoryItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }
}