package com.example.tugassoavincentardyanputra2101658344.common.viewholder

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.tugassoavincentardyanputra2101658344.R
import com.example.tugassoavincentardyanputra2101658344.databinding.ListItemBinding
import com.example.tugassoavincentardyanputra2101658344.extension.ItemViewStyle
import com.example.tugassoavincentardyanputra2101658344.extension.animateClicked
import com.example.tugassoavincentardyanputra2101658344.extension.loadImage
import com.example.tugassoavincentardyanputra2101658344.util.BaseItem
import com.example.tugassoavincentardyanputra2101658344.util.BaseViewHolder
import com.example.tugassoavincentardyanputra2101658344.util.ViewHolderFactory

data class ListItem(
    val imageUrl: String,
    val foodName: String,
    val area: String = "",
    val category: String = "",
    val idMeal: String = "",
    val isCategoryList: Boolean = false,
    val isFromMostPopularFood: Boolean = false,
    var isDeleted: Boolean = false
) : BaseItem, ItemViewStyle<ListItem>() {
    override fun layoutId(): Int = R.layout.list_item
    override fun id(): Long = imageUrl.hashCode().toLong()
    override fun contentsTheSame(newItem: BaseItem): Boolean = if (newItem is ListItem)
        toString() == newItem.toString() else false
}

class ListViewHolder(
    private val itemBinding: ListItemBinding
) : BaseViewHolder<ListItem>(itemBinding.root) {

    override fun bind(item: ListItem, listener: (BaseItem, View) -> Unit) {
        itemBinding.apply {
            constraintListItemWrapper.setOnClickListener { listener(item, it) }

            imageFoodItem.loadImage(item.imageUrl)
            textFoodName.text = item.foodName
            if (item.isCategoryList) {
                imageArea.visibility = View.GONE
                textArea.visibility = View.GONE
                textFoodCategory.visibility = View.GONE
                imageDelete.visibility = View.GONE
                verticalLineDivider.visibility = View.GONE
                textIdMeal.apply {
                    visibility = View.VISIBLE
                    text = context.getString(R.string.id_meal, item.idMeal)
                }
            } else {
                textArea.text = item.area
                textFoodCategory.text = item.category
            }

            imageDelete.setOnClickListener {
                imageDelete.animateClicked()
                item.isDeleted = true
                listener(item, it)
            }
        }
        item.applyContainerStyle(itemView)
    }
}

class ListViewHolderFactory : ViewHolderFactory() {
    override fun layoutId(): Int = R.layout.list_item

    override fun bindView(
        item: BaseItem,
        viewHolder: BaseViewHolder<*>,
        listener: (BaseItem, View) -> Unit
    ) {
        (viewHolder as ListViewHolder).bind(item as ListItem, listener)
    }

    override fun createViewHolder(parent: ViewGroup): BaseViewHolder<*> {
        return ListViewHolder(
            ListItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }
}