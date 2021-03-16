package com.example.tugassoavincentardyanputra2101658344.ui.favorites.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.viewModelScope
import com.example.tugassoavincentardyanputra2101658344.common.viewholder.ListItem
import com.example.tugassoavincentardyanputra2101658344.common.viewholder.NoDataItem
import com.example.tugassoavincentardyanputra2101658344.foundation.BaseViewModel
import com.example.tugassoavincentardyanputra2101658344.repository.FavoriteRepository
import com.example.tugassoavincentardyanputra2101658344.repository.MostPopularFoodRepository
import com.example.tugassoavincentardyanputra2101658344.util.BaseItem
import com.example.tugassoavincentardyanputra2101658344.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavoriteViewModel @Inject constructor(
    private val mostPopularFoodRepository: MostPopularFoodRepository,
    private val favoriteRepository: FavoriteRepository
) : BaseViewModel() {

    private val _refresh: MutableLiveData<Boolean> = MutableLiveData()
    val favoriteItem: LiveData<Resource<List<BaseItem>>> = Transformations.switchMap(_refresh) {
        getListItem()
    }

    private val _isUpdated: MutableLiveData<Resource<Boolean>> = MutableLiveData()
    val isUpdated: LiveData<Resource<Boolean>> = _isUpdated

    private fun getListItem(): LiveData<Resource<List<BaseItem>>> {
        val itemFromDB = favoriteRepository.allFavoriteData
        val items: MutableList<ListItem> = mutableListOf()
        return Transformations.map(itemFromDB) { itm ->
            items.clear()
            itm.forEachIndexed { idx, it ->
                if (idx == 0) {
                    items.add(
                        ListItem(
                            it.strMealThumb ?: "",
                            it.strMeal ?: "",
                            it.strArea ?: "",
                            it.strCategory ?: "",
                            it.idMeal,
                            isFromMostPopularFood = it.isFromMostPopularFood
                        )
                    )
                } else {
                    items.add(
                        ListItem(
                            it.strMealThumb ?: "",
                            it.strMeal ?: "",
                            it.strArea ?: "",
                            it.strCategory ?: "",
                            it.idMeal,
                            isFromMostPopularFood = it.isFromMostPopularFood
                        )
                    )
                }
            }

            if (itm.isEmpty() || items.isEmpty()) Resource.empty(listOf(NoDataItem()))
            else Resource.success(items)
        }
    }

    fun deleteFavorite(idMeal: String, isFromMostPopularFood: Boolean = false) {
        _isUpdated.value = Resource.loading(null)
        val job = viewModelScope.launch {
            if (isFromMostPopularFood) mostPopularFoodRepository.updateFavoriteField(false, idMeal)
            favoriteRepository.deleteFavoriteById(idMeal)
        }

        viewModelScope.launch {
            job.join()
            _isUpdated.value = Resource.success(true)
        }
    }

    fun refresh() {
        _refresh.value = true
    }
}