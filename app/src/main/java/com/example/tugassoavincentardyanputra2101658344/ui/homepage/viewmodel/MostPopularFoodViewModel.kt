package com.example.tugassoavincentardyanputra2101658344.ui.homepage.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import com.airbnb.paris.extensions.layoutMarginLeftDp
import com.airbnb.paris.extensions.viewGroupStyle
import com.example.tugassoavincentardyanputra2101658344.db.entity.MostPopularFood
import com.example.tugassoavincentardyanputra2101658344.foundation.BaseViewModel
import com.example.tugassoavincentardyanputra2101658344.repository.MostPopularFoodRepository
import com.example.tugassoavincentardyanputra2101658344.ui.homepage.viewholder.MostPopularFoodItem
import com.example.tugassoavincentardyanputra2101658344.util.Resource
import com.example.tugassoavincentardyanputra2101658344.util.Status
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MostPopularFoodViewModel @Inject constructor(
    private val mostPopularFoodRepository: MostPopularFoodRepository
) : BaseViewModel(), HomepageViewModel {

    private val _refresh: MutableLiveData<Boolean> = MutableLiveData()

    val mostPopularFood: LiveData<Resource<List<MostPopularFoodItem>>> = Transformations
        .switchMap(_refresh) { getMostPopularFoodItem() }

    private fun getMostPopularFoodItem(): LiveData<Resource<List<MostPopularFoodItem>>> {
        val itemFromDB = mostPopularFoodRepository.loadMeals()
        val item: MutableList<MostPopularFoodItem> = mutableListOf()
        return Transformations.map(itemFromDB) {
            when (it.status) {
                Status.LOADING -> Resource.loading(item)
                Status.SUCCESS -> {
                    item.clear()
                    (it.data as List<MostPopularFood>).forEachIndexed { idx, foodItem ->
                        if (idx == 0) {
                            item.add(
                                MostPopularFoodItem(foodItem)
                                    .setContainerStyle(viewGroupStyle {
                                        layoutMarginLeftDp(23)
                                    })
                            )
                        } else item.add(MostPopularFoodItem(foodItem))
                    }
                    Resource.success(item)
                }
                Status.ERROR -> Resource.error(it.message ?: "ERROR", item)
                else -> Resource.empty(null)
            }
        }
    }

    override fun refresh() {
        _refresh.value = true
    }
}