package com.example.tugassoavincentardyanputra2101658344.ui.homepage.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import com.airbnb.paris.extensions.layoutMarginLeftDp
import com.airbnb.paris.extensions.viewGroupStyle
import com.example.tugassoavincentardyanputra2101658344.db.entity.BestFoodOfTheDay
import com.example.tugassoavincentardyanputra2101658344.foundation.BaseViewModel
import com.example.tugassoavincentardyanputra2101658344.repository.FoodOfTheDayRepository
import com.example.tugassoavincentardyanputra2101658344.ui.homepage.viewholder.FoodOfTheDayItem
import com.example.tugassoavincentardyanputra2101658344.util.Resource
import com.example.tugassoavincentardyanputra2101658344.util.Status
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class FoodOfTheDayViewModel @Inject constructor(
    private val foodOfTheDayRepository: FoodOfTheDayRepository
) : BaseViewModel(), HomepageViewModel {

    private val _refresh: MutableLiveData<Boolean> = MutableLiveData()

    val foodOfTheDay: LiveData<Resource<List<FoodOfTheDayItem>>> = Transformations
        .switchMap(_refresh) { getFoodOfTheDayItem() }

    private fun getFoodOfTheDayItem(): LiveData<Resource<List<FoodOfTheDayItem>>> {
        val itemFromDB = foodOfTheDayRepository.loadBestFoodOfTheDay()
        val item: MutableList<FoodOfTheDayItem> = mutableListOf()
        return Transformations.map(itemFromDB) {
            when (it.status) {
                Status.LOADING -> Resource.loading(item)
                Status.SUCCESS -> {
                    item.clear()
                    item.add(
                        FoodOfTheDayItem((it.data as BestFoodOfTheDay))
                            .setContainerStyle(viewGroupStyle {
                                layoutMarginLeftDp(23)
                            })
                    )
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