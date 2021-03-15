package com.example.tugassoavincentardyanputra2101658344.ui.homepage.viewholder

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.tugassoavincentardyanputra2101658344.R
import com.example.tugassoavincentardyanputra2101658344.databinding.NestedHorizontalRecyclerItemBinding
import com.example.tugassoavincentardyanputra2101658344.extension.ItemViewStyle
import com.example.tugassoavincentardyanputra2101658344.util.BaseItem
import com.example.tugassoavincentardyanputra2101658344.util.BaseRecyclerAdapter
import com.example.tugassoavincentardyanputra2101658344.util.BaseViewHolder
import com.example.tugassoavincentardyanputra2101658344.util.ViewHolderFactory

data class NestedHorizontalRecyclerItem(
    val listCategoryItem: List<FeatureCategoryItem>? = null,
    val listMpfItem: List<MostPopularFoodItem>? = null,
    val isMpf: Boolean = false,
    var isFavoriteAfterClicked: Boolean = false
) : BaseItem, ItemViewStyle<NestedHorizontalRecyclerItem>() {
    override fun layoutId(): Int = R.layout.nested_horizontal_recycler_item
    override fun id(): Long = layoutId().hashCode().toLong()
    override fun contentsTheSame(newItem: BaseItem): Boolean =
        if (newItem is NestedHorizontalRecyclerItem) {
            if (isMpf) {
                if (listMpfItem != null) listMpfItem.toString() != newItem.listMpfItem.toString()
                else false
            } else {
                if (listCategoryItem != null) listCategoryItem.toString() == newItem.listCategoryItem.toString()
                else false
            }
        } else false
}

class NestedHorizontalRecyclerViewHolder(
    itemBinding: NestedHorizontalRecyclerItemBinding
) : BaseViewHolder<NestedHorizontalRecyclerItem>(itemBinding.root) {

    private val listener: (BaseItem, View) -> Unit = { _, _ -> }
    private val recyclerAdapter = BaseRecyclerAdapter(
        listener, listOf(
            FeatureCategoryViewHolderFactory(),
            MostPopularFoodViewHolderFactory()
        )
    )

    init {
        itemBinding.recyclerNestedHorizontalWrapper.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            adapter = recyclerAdapter
        }
    }

    override fun bind(item: NestedHorizontalRecyclerItem, listener: (BaseItem, View) -> Unit) {
        if (item.isMpf) recyclerAdapter.submitList(item.listMpfItem)
        else recyclerAdapter.submitList(item.listCategoryItem)
        item.applyContainerStyle(itemView)

        recyclerAdapter.setOnItemClicked { baseItem, view ->
            when (baseItem) {
                is MostPopularFoodItem -> {
                    when (view.id) {
                        R.id.imageFavorite -> listener(baseItem, view)
                        R.id.cardMostPopularFoodItemWrapper -> listener(baseItem, view)
                    }
                }
                is FeatureCategoryItem -> listener(baseItem, view)
            }
        }
    }
}

class NestedHorizontalRecyclerViewHolderFactory : ViewHolderFactory() {
    override fun layoutId(): Int = R.layout.nested_horizontal_recycler_item

    override fun bindView(
        item: BaseItem,
        viewHolder: BaseViewHolder<*>,
        listener: (BaseItem, View) -> Unit
    ) {
        (viewHolder as NestedHorizontalRecyclerViewHolder).bind(
            item as NestedHorizontalRecyclerItem,
            listener
        )
    }

    override fun createViewHolder(parent: ViewGroup): BaseViewHolder<*> {
        return NestedHorizontalRecyclerViewHolder(
            NestedHorizontalRecyclerItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }
}