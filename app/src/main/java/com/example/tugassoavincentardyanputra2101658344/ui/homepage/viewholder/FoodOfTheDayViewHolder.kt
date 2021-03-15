package com.example.tugassoavincentardyanputra2101658344.ui.homepage.viewholder

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import com.example.tugassoavincentardyanputra2101658344.R
import com.example.tugassoavincentardyanputra2101658344.databinding.FoodOfTheDayItemBinding
import com.example.tugassoavincentardyanputra2101658344.db.entity.BestFoodOfTheDay
import com.example.tugassoavincentardyanputra2101658344.extension.ItemViewStyle
import com.example.tugassoavincentardyanputra2101658344.extension.animateClicked
import com.example.tugassoavincentardyanputra2101658344.extension.getCategory
import com.example.tugassoavincentardyanputra2101658344.extension.loadImage
import com.example.tugassoavincentardyanputra2101658344.util.BaseItem
import com.example.tugassoavincentardyanputra2101658344.util.BaseViewHolder
import com.example.tugassoavincentardyanputra2101658344.util.ViewHolderFactory

data class FoodOfTheDayItem(
    val bfItem: BestFoodOfTheDay,
    var isFavoriteAfterClicked: Boolean = false
) : BaseItem, ItemViewStyle<FoodOfTheDayItem>() {
    override fun layoutId(): Int = R.layout.food_of_the_day_item
    override fun id(): Long = bfItem.strImageSource.hashCode().toLong()
    override fun contentsTheSame(newItem: BaseItem): Boolean = if (newItem is FoodOfTheDayItem) {
        toString() == newItem.toString()
    } else false
}

class FoodOfTheDayViewHolder(
    private val itemBinding: FoodOfTheDayItemBinding
) : BaseViewHolder<FoodOfTheDayItem>(itemBinding.root) {

    private val favoriteBG = ContextCompat.getDrawable(getContext(), R.drawable.ic_favorite)

    override fun bind(item: FoodOfTheDayItem, listener: (BaseItem, View) -> Unit) {
        itemBinding.apply {
            item.bfItem.apply {
                textArea.text = strArea
                if (strMealThumb != null) {
                    imageFood.loadImage(strMealThumb)
                }
                textFoodTitle.text = strMeal
                if (strCategory != null) textCategory.text = strCategory.getCategory()
                else textCategory.visibility = View.GONE
                if (isFavorite) imageFavorite.setImageResource(R.drawable.ic_favorite)
                else imageFavorite.setImageResource(R.drawable.ic_unfavorite)
            }

            cardFoodOfTheDayItemWrapper.setOnClickListener { listener(item, it) }

            imageFavorite.setOnClickListener {
                imageFavorite.animateClicked()
                item.isFavoriteAfterClicked =
                    if (imageFavorite.drawable.constantState != favoriteBG?.constantState) {
                        imageFavorite.setImageResource(R.drawable.ic_favorite)
                        true
                    } else {
                        imageFavorite.setImageResource(R.drawable.ic_unfavorite)
                        false
                    }
                listener(item, it)
            }
        }
        item.applyContainerStyle(itemView)
    }
}

class FoodOfTheDayViewHolderFactory : ViewHolderFactory() {
    override fun layoutId(): Int = R.layout.food_of_the_day_item

    override fun bindView(
        item: BaseItem,
        viewHolder: BaseViewHolder<*>,
        listener: (BaseItem, View) -> Unit
    ) {
        (viewHolder as FoodOfTheDayViewHolder).bind(item as FoodOfTheDayItem, listener)
    }

    override fun createViewHolder(parent: ViewGroup): BaseViewHolder<*> {
        return FoodOfTheDayViewHolder(
            FoodOfTheDayItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }
}