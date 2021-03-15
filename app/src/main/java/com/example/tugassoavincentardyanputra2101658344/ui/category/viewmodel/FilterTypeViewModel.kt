package com.example.tugassoavincentardyanputra2101658344.ui.category.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.airbnb.paris.extensions.layoutMarginBottomDp
import com.airbnb.paris.extensions.layoutMarginLeftDp
import com.airbnb.paris.extensions.layoutMarginTopDp
import com.airbnb.paris.extensions.viewGroupStyle
import com.example.tugassoavincentardyanputra2101658344.R
import com.example.tugassoavincentardyanputra2101658344.common.viewholder.HeaderSectionItem
import com.example.tugassoavincentardyanputra2101658344.common.viewholder.NoDataItem
import com.example.tugassoavincentardyanputra2101658344.db.entity.FilterType
import com.example.tugassoavincentardyanputra2101658344.foundation.BaseViewModel
import com.example.tugassoavincentardyanputra2101658344.repository.FilterTypeRepository
import com.example.tugassoavincentardyanputra2101658344.ui.category.viewholder.FilterTypeItem
import com.example.tugassoavincentardyanputra2101658344.util.BaseItem
import com.example.tugassoavincentardyanputra2101658344.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FilterTypeViewModel @Inject constructor(
    private val filterTypeRepository: FilterTypeRepository
) : BaseViewModel() {

    private val _filterTypeItem: MutableLiveData<Resource<List<BaseItem>>> = MutableLiveData()
    val filterTypeItem: LiveData<Resource<List<BaseItem>>> = _filterTypeItem

    private var filterName = ""
    private var filterType = ""

    fun getFilter() {
        _filterTypeItem.value = Resource.loading(null)

        var item: List<FilterType> = listOf()
        val mainJob = viewModelScope.launch {
            val job = async { filterTypeRepository.getAllFilterType() }
            item = job.await()
        }

        viewModelScope.launch {
            mainJob.join()
            if (item.isNotEmpty()) _filterTypeItem.value = mapFilterTypeItem(item)
            else _filterTypeItem.value = Resource.empty(listOf(NoDataItem()))
        }
    }

    private fun mapFilterTypeItem(listItem: List<FilterType>): Resource<List<BaseItem>> {
        val items: MutableList<BaseItem> = mutableListOf()
        var currentFilterType = ""
        listItem.forEach {
            if (it.filterType != currentFilterType) {
                currentFilterType = it.filterType ?: "Unknown filter type"
                items.add(
                    HeaderSectionItem(
                        it.filterType ?: "Unknown filter type"
                    ).setContainerStyle(viewGroupStyle {
                        layoutMarginLeftDp(23)
                        layoutMarginTopDp(28)
                        layoutMarginBottomDp(16)
                    }).setTextStyleWithStyleResId(R.style.OlsoTextViewStyle18)
                )
            }

            items.add(
                FilterTypeItem(
                    it.filterName,
                    it.filterType ?: "Unknown filter type"
                )
            )
        }

        return if (items.isEmpty()) Resource.empty(listOf(NoDataItem()))
        else Resource.success(items)
    }

    fun setFilterName(filterName: String) {
        this.filterName = filterName
    }

    fun setFilterType(filterType: String) {
        this.filterType = filterType
    }

    fun getFilterName() = filterName

    fun getFilterType() = filterType
}