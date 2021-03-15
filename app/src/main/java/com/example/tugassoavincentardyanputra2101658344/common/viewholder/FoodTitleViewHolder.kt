package com.example.tugassoavincentardyanputra2101658344.common.viewholder

import android.text.SpannableString
import android.text.style.UnderlineSpan
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.tugassoavincentardyanputra2101658344.R
import com.example.tugassoavincentardyanputra2101658344.databinding.FoodTitleItemBinding
import com.example.tugassoavincentardyanputra2101658344.extension.ItemViewStyle
import com.example.tugassoavincentardyanputra2101658344.util.BaseItem
import com.example.tugassoavincentardyanputra2101658344.util.BaseViewHolder
import com.example.tugassoavincentardyanputra2101658344.util.ViewHolderFactory

data class FoodTitleItem(
    val title: String,
    val additionalWhiteText: String,
    val needUnderlined: Boolean = false,
    val additionalInfo: String? = null
) : BaseItem, ItemViewStyle<FoodTitleItem>() {
    override fun layoutId(): Int = R.layout.food_title_item
    override fun id(): Long = title.hashCode().toLong()
    override fun contentsTheSame(newItem: BaseItem): Boolean =
        title == (newItem as FoodTitleItem).title
}

class FoodTitleViewHolder(
    private val itemBinding: FoodTitleItemBinding
) : BaseViewHolder<FoodTitleItem>(itemBinding.root) {

    override fun bind(item: FoodTitleItem, listener: (BaseItem, View) -> Unit) {
        itemBinding.apply {
            textTitle.text = if (item.needUnderlined) setUnderlineText(item.title) else item.title
            textAdditionalWhiteText.text = item.additionalWhiteText
            item.additionalInfo?.let {
                textAdditionalOLSOText.apply {
                    visibility = View.VISIBLE
                    text = it
                }
            }
        }
        item.applyContainerStyle(itemView)
    }

    private fun setUnderlineText(text: String): SpannableString {
        val content = SpannableString(text)
        content.setSpan(UnderlineSpan(), 0, content.length, 0)
        return content
    }
}

class FoodTitleViewHolderFactory : ViewHolderFactory() {

    override fun layoutId(): Int = R.layout.food_title_item

    override fun bindView(
        item: BaseItem,
        viewHolder: BaseViewHolder<*>,
        listener: (BaseItem, View) -> Unit
    ) {
        (viewHolder as FoodTitleViewHolder).bind(item as FoodTitleItem, listener)
    }

    override fun createViewHolder(parent: ViewGroup): BaseViewHolder<*> {
        return FoodTitleViewHolder(
            FoodTitleItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }
}
