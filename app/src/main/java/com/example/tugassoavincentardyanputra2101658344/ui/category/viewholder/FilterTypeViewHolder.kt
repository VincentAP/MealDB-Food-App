package com.example.tugassoavincentardyanputra2101658344.ui.category.viewholder

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.tugassoavincentardyanputra2101658344.R
import com.example.tugassoavincentardyanputra2101658344.databinding.FilterTypeItemBinding
import com.example.tugassoavincentardyanputra2101658344.util.*

data class FilterTypeItem(
    val filterName: String,
    val filterType: String
) : BaseItem {
    override fun layoutId(): Int = R.layout.filter_type_item
    override fun id(): Long = filterName.hashCode().toLong()
    override fun contentsTheSame(newItem: BaseItem): Boolean =
        if (newItem is FilterTypeItem) filterName == newItem.filterName
        else false
}

class FilterTypeViewHolder(
    private val itemBinding: FilterTypeItemBinding
) : BaseViewHolder<FilterTypeItem>(itemBinding.root) {

    private var sharePref: SharePref = SharePref(getContext())

    override fun bind(item: FilterTypeItem, listener: (BaseItem, View) -> Unit) {
        val chosenFilter: String = sharePref.get(Constant.SELECTED_FILTER_TYPE, "Breakfast")
        itemBinding.textFilterName.text = item.filterName

        itemBinding.imageRadioButtonSelector.apply {
            if (item.filterName.equals(chosenFilter, true))
                setImageResource(R.drawable.radio_button_selected_bg)
            else setImageResource(R.drawable.radio_button_unselected_bg)
        }

        itemBinding.constraintFilterTypeItemWrapper.setOnClickListener {
            sharePref.put(Constant.SELECTED_FILTER_TYPE, item.filterName)
            listener(item, it)
        }
    }
}

class FilterTypeViewHolderFactory : ViewHolderFactory() {
    override fun layoutId(): Int = R.layout.filter_type_item

    override fun bindView(
        item: BaseItem,
        viewHolder: BaseViewHolder<*>,
        listener: (BaseItem, View) -> Unit
    ) {
        (viewHolder as FilterTypeViewHolder).bind(item as FilterTypeItem, listener)
    }

    override fun createViewHolder(parent: ViewGroup): BaseViewHolder<*> {
        return FilterTypeViewHolder(
            FilterTypeItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }
}