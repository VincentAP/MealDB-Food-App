package com.example.tugassoavincentardyanputra2101658344.ui.homepage.viewholder

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import com.example.tugassoavincentardyanputra2101658344.R
import com.example.tugassoavincentardyanputra2101658344.databinding.MostPopularFoodItemBinding
import com.example.tugassoavincentardyanputra2101658344.db.entity.MostPopularFood
import com.example.tugassoavincentardyanputra2101658344.extension.ItemViewStyle
import com.example.tugassoavincentardyanputra2101658344.extension.animateClicked
import com.example.tugassoavincentardyanputra2101658344.extension.getCategory
import com.example.tugassoavincentardyanputra2101658344.extension.loadImage
import com.example.tugassoavincentardyanputra2101658344.util.BaseItem
import com.example.tugassoavincentardyanputra2101658344.util.BaseViewHolder
import com.example.tugassoavincentardyanputra2101658344.util.ViewHolderFactory

data class MostPopularFoodItem(
    val mpfItem: MostPopularFood,
    var isFavoriteAfterClicked: Boolean = false
) : BaseItem, ItemViewStyle<MostPopularFoodItem>() {
    override fun layoutId(): Int = R.layout.most_popular_food_item
    override fun id(): Long = mpfItem.idMeal.hashCode().toLong()
    override fun contentsTheSame(newItem: BaseItem): Boolean = if (newItem is MostPopularFoodItem) {
        mpfItem.isFavorite == newItem.mpfItem.isFavorite &&
                mpfItem.idMeal == newItem.mpfItem.idMeal
    } else false
}

class MostPopularFoodViewHolder(
    private val itemBinding: MostPopularFoodItemBinding
) : BaseViewHolder<MostPopularFoodItem>(itemBinding.root) {

    private val favoriteBG = ContextCompat.getDrawable(getContext(), R.drawable.ic_favorite)

    override fun bind(item: MostPopularFoodItem, listener: (BaseItem, View) -> Unit) {
        itemBinding.apply {
            item.mpfItem.apply {
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
            cardMostPopularFoodItemWrapper.setOnClickListener { listener(item, it) }
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

class MostPopularFoodViewHolderFactory : ViewHolderFactory() {

    override fun layoutId(): Int = R.layout.most_popular_food_item

    override fun bindView(
        item: BaseItem,
        viewHolder: BaseViewHolder<*>,
        listener: (BaseItem, View) -> Unit
    ) {
        (viewHolder as MostPopularFoodViewHolder).bind(item as MostPopularFoodItem, listener)
    }

    override fun createViewHolder(parent: ViewGroup): BaseViewHolder<*> {
        return MostPopularFoodViewHolder(
            MostPopularFoodItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }
}
