package com.example.tugassoavincentardyanputra2101658344.ui.homepage.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.airbnb.paris.extensions.layoutMarginBottomDp
import com.airbnb.paris.extensions.layoutMarginLeftDp
import com.airbnb.paris.extensions.layoutMarginTopDp
import com.airbnb.paris.extensions.viewGroupStyle
import com.example.tugassoavincentardyanputra2101658344.common.viewholder.FoodTitleItem
import com.example.tugassoavincentardyanputra2101658344.common.viewholder.HeaderSectionItem
import com.example.tugassoavincentardyanputra2101658344.db.entity.BestFoodOfTheDay
import com.example.tugassoavincentardyanputra2101658344.db.entity.MostPopularFood
import com.example.tugassoavincentardyanputra2101658344.foundation.BaseViewModel
import com.example.tugassoavincentardyanputra2101658344.repository.FavoriteRepository
import com.example.tugassoavincentardyanputra2101658344.repository.FoodOfTheDayRepository
import com.example.tugassoavincentardyanputra2101658344.repository.MostPopularFoodRepository
import com.example.tugassoavincentardyanputra2101658344.ui.homepage.viewholder.FeatureCategoryItem
import com.example.tugassoavincentardyanputra2101658344.ui.homepage.viewholder.FoodOfTheDayItem
import com.example.tugassoavincentardyanputra2101658344.ui.homepage.viewholder.MostPopularFoodItem
import com.example.tugassoavincentardyanputra2101658344.ui.homepage.viewholder.NestedHorizontalRecyclerItem
import com.example.tugassoavincentardyanputra2101658344.util.BaseItem
import com.example.tugassoavincentardyanputra2101658344.util.Resource
import com.example.tugassoavincentardyanputra2101658344.util.toFavorite
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.schedulers.Schedulers
import kotlinx.coroutines.launch
import javax.inject.Inject

interface HomepageViewModel {
    fun refresh()
}

@HiltViewModel
class HomepageViewModelImpl @Inject constructor(
    private val favoriteRepository: FavoriteRepository,
    private val foodOfTheDayRepository: FoodOfTheDayRepository,
    private val mostPopularFoodRepository: MostPopularFoodRepository
) : BaseViewModel() {

    private val _homepageItem: MutableLiveData<Resource<List<BaseItem>>> = MutableLiveData()
    val homepageItem: LiveData<Resource<List<BaseItem>>> = _homepageItem

    private val initialItemList: MutableList<BaseItem> = mutableListOf()
    private val categoryItemList: MutableList<BaseItem> = mutableListOf()
    private val foodOfTheDayItemList: MutableList<BaseItem> = mutableListOf()
    private val mostPopularFoodItemList: MutableList<BaseItem> = mutableListOf()

    private val baseItemList: MutableList<BaseItem> = mutableListOf()

    fun setInitialItem() {
        initialItemList.add(
            FoodTitleItem(
                "Food Menu",
                "Start cooking today!",
                true
            )
        )
        initialItemList.add(
            HeaderSectionItem(
                "Features",
                "Challenge yourself! Generate random food for you to cook today."
            ).setContainerStyle(viewGroupStyle {
                layoutMarginLeftDp(23)
                layoutMarginTopDp(13)
                layoutMarginBottomDp(13)
            })
        )
        initialItemList.add(
            FeatureCategoryItem(
                "https://www.themealdb.com/images/media/meals/uuuspp1468263334.jpg",
                "Random food",
                false
            ).setContainerStyle(viewGroupStyle {
                layoutMarginLeftDp(23)
                layoutMarginBottomDp(30)
            })
        )
    }

    private fun refresh() {
        baseItemList.apply {
            clear()
            addAll(initialItemList)
            addAll(categoryItemList)
            addAll(foodOfTheDayItemList)
            addAll(mostPopularFoodItemList)
        }
        _homepageItem.value = Resource.success(baseItemList)
    }

    fun setFavorite(
        foodOfTheDayItem: BestFoodOfTheDay? = null,
        mostPopularFoodItem: MostPopularFood? = null
    ) {
        val favorite = if (foodOfTheDayItem != null) {
            foodOfTheDayItem.let {
                viewModelScope.launch {
                    foodOfTheDayRepository.updateFavoriteField(true, it.idMeal)
                }
                it.toFavorite()
            }
        } else {
            mostPopularFoodItem?.let {
                viewModelScope.launch {
                    mostPopularFoodRepository.updateFavoriteField(true, it.idMeal)
                }
                it.toFavorite()
            }
        }

        favorite?.let {
            viewModelScope.launch {
                favoriteRepository.insertFavorite(it)
            }
        }
    }

    fun deleteFavorite(idMeal: String, isFromMostPopularFood: Boolean = false) {
        viewModelScope.launch {
            if (isFromMostPopularFood) mostPopularFoodRepository.updateFavoriteField(false, idMeal)
            favoriteRepository.deleteFavoriteById(idMeal)
        }
    }

    fun setData(baseItem: List<BaseItem>) {
        _homepageItem.value = Resource.loading(null)
        Observable.just(baseItem)
            .subscribeOn(Schedulers.io())
            .observeOn(Schedulers.io())
            .map { mapBaseItemData(it) }
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                refresh()
            }, {
                it.printStackTrace()
                _homepageItem.value = Resource.error("Unknown error", baseItemList)
            }).addToDisposable()
    }

    @Suppress("UNCHECKED_CAST")
    private fun mapBaseItemData(baseItem: List<BaseItem>) {
        when (baseItem[0]) {
            is FeatureCategoryItem -> {
                categoryItemList.clear()
                categoryItemList.add(
                    HeaderSectionItem("Categories")
                        .setContainerStyle(viewGroupStyle {
                            layoutMarginLeftDp(23)
                            layoutMarginBottomDp(10)
                        })
                )
                categoryItemList.add(
                    NestedHorizontalRecyclerItem(
                        (baseItem as List<FeatureCategoryItem>)
                    ).setContainerStyle(viewGroupStyle {
                        layoutMarginBottomDp(30)
                    })
                )
            }
            is FoodOfTheDayItem -> {
                foodOfTheDayItemList.clear()
                foodOfTheDayItemList.add(
                    HeaderSectionItem("Food of the day")
                        .setContainerStyle(viewGroupStyle {
                            layoutMarginLeftDp(23)
                            layoutMarginBottomDp(10)
                        })
                )
                baseItem.forEach {
                    foodOfTheDayItemList.add(it)
                }
            }
            is MostPopularFoodItem -> {
                mostPopularFoodItemList.clear()
                mostPopularFoodItemList.add(
                    HeaderSectionItem("Most popular food")
                        .setContainerStyle(viewGroupStyle {
                            layoutMarginLeftDp(23)
                            layoutMarginBottomDp(10)
                            layoutMarginTopDp(30)
                        })
                )
                mostPopularFoodItemList.add(
                    NestedHorizontalRecyclerItem(
                        listMpfItem = (baseItem as List<MostPopularFoodItem>),
                        isMpf = true
                    ).setContainerStyle(viewGroupStyle {
                        layoutMarginBottomDp(50)
                    })
                )
            }
        }
    }
}