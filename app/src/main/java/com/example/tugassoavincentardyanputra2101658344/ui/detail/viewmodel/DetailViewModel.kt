package com.example.tugassoavincentardyanputra2101658344.ui.detail.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.airbnb.paris.extensions.layoutMarginBottomDp
import com.airbnb.paris.extensions.viewGroupStyle
import com.example.tugassoavincentardyanputra2101658344.api.model.MealBaseResponse
import com.example.tugassoavincentardyanputra2101658344.api.model.MealItemResponse
import com.example.tugassoavincentardyanputra2101658344.common.viewholder.AdditionalSpaceItem
import com.example.tugassoavincentardyanputra2101658344.common.viewholder.FoodTitleItem
import com.example.tugassoavincentardyanputra2101658344.common.viewholder.NoDataItem
import com.example.tugassoavincentardyanputra2101658344.db.entity.AllMealItem
import com.example.tugassoavincentardyanputra2101658344.foundation.BaseViewModel
import com.example.tugassoavincentardyanputra2101658344.repository.*
import com.example.tugassoavincentardyanputra2101658344.ui.detail.viewholder.DetailImageItem
import com.example.tugassoavincentardyanputra2101658344.ui.detail.viewholder.DetailListItem
import com.example.tugassoavincentardyanputra2101658344.util.*
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    private val favoriteRepository: FavoriteRepository,
    private val foodOfTheDayRepository: FoodOfTheDayRepository,
    private val mostPopularFoodRepository: MostPopularFoodRepository,
    private val detailRepository: DetailRepository,
    private val allMealItemRepository: AllMealItemRepository
) : BaseViewModel() {

    private val _detailItem: MutableLiveData<Resource<List<BaseItem>>> = MutableLiveData()
    val detailItem: LiveData<Resource<List<BaseItem>>> = _detailItem

    private var itemSource: String? = null
    private var itemYoutubeSource: String? = null
    private var idMeal: String? = null
    private val currentItemList: MutableList<AllMealItem> = mutableListOf()

    private var mealItemResponse: MealItemResponse? = null

    fun getBestFoodOfTheDayItem(idMeal: String) {
        _detailItem.value = Resource.loading(null)
        var item: List<BaseItem> = listOf()
        val mainJob = viewModelScope.launch {
            val job = async { foodOfTheDayRepository.getFoodOfTheDayById(idMeal) }
            item = mapFoodItemById(job.await())
        }

        viewModelScope.launch {
            mainJob.join()
            if (item.isNotEmpty()) _detailItem.value = Resource.success(item)
            else _detailItem.value = Resource.empty(null)
        }
    }

    fun getMostPopularFoodItem(idMeal: String) {
        _detailItem.value = Resource.loading(null)
        var item: List<BaseItem> = listOf()
        val mainJob = viewModelScope.launch {
            val job = async { mostPopularFoodRepository.getMostPopularFoodById(idMeal) }
            item = mapFoodItemById(job.await())
        }

        viewModelScope.launch {
            mainJob.join()
            if (item.isNotEmpty()) _detailItem.value = Resource.success(item)
            else _detailItem.value = Resource.empty(null)
        }
    }

    private fun <T> mapFoodItemById(item: T): List<BaseItem> {
        val items: MutableList<BaseItem> = mutableListOf()
        item.toMealItemResponse()?.let {
            mealItemResponse = it
            idMeal = it.idMeal
            itemSource = it.strSource
            itemYoutubeSource = it.strYoutube
            items.add(DetailImageItem(it.strMealThumb ?: ""))
            items.add(
                FoodTitleItem(
                    it.strMeal ?: "Delicious",
                    it.strCategory ?: "Category",
                    additionalInfo = "tags: ${it.strTags ?: "No Tag"}"
                ).setContainerStyle(viewGroupStyle {
                    layoutMarginBottomDp(23)
                })
            )
            items.add(DetailListItem("Ingredients:"))

            // INGREDIENTS
            if (it.strIngredient1?.isNotEmpty() == true)
                items.add(DetailListItem("- ${it.strIngredient1} (${it.strMeasure1})"))

            if (it.strIngredient2?.isNotEmpty() == true)
                items.add(DetailListItem("- ${it.strIngredient2} (${it.strMeasure2})"))

            if (it.strIngredient3?.isNotEmpty() == true)
                items.add(DetailListItem("- ${it.strIngredient3} (${it.strMeasure3})"))

            if (it.strIngredient4?.isNotEmpty() == true)
                items.add(DetailListItem("- ${it.strIngredient4} (${it.strMeasure4})"))

            if (it.strIngredient5?.isNotEmpty() == true)
                items.add(DetailListItem("- ${it.strIngredient5} (${it.strMeasure5})"))

            if (it.strIngredient6?.isNotEmpty() == true)
                items.add(DetailListItem("- ${it.strIngredient6} (${it.strMeasure6})"))

            if (it.strIngredient7?.isNotEmpty() == true)
                items.add(DetailListItem("- ${it.strIngredient7} (${it.strMeasure7})"))

            if (it.strIngredient8?.isNotEmpty() == true)
                items.add(DetailListItem("- ${it.strIngredient8} (${it.strMeasure8})"))

            if (it.strIngredient9?.isNotEmpty() == true)
                items.add(DetailListItem("- ${it.strIngredient9} (${it.strMeasure9})"))

            if (it.strIngredient10?.isNotEmpty() == true)
                items.add(DetailListItem("- ${it.strIngredient10} (${it.strMeasure10})"))

            if (it.strIngredient11?.isNotEmpty() == true)
                items.add(DetailListItem("- ${it.strIngredient11} (${it.strMeasure11})"))

            if (it.strIngredient12?.isNotEmpty() == true)
                items.add(DetailListItem("- ${it.strIngredient12} (${it.strMeasure12})"))

            if (it.strIngredient13?.isNotEmpty() == true)
                items.add(DetailListItem("- ${it.strIngredient13} (${it.strMeasure13})"))

            if (it.strIngredient14?.isNotEmpty() == true)
                items.add(DetailListItem("- ${it.strIngredient14} (${it.strMeasure14})"))

            if (it.strIngredient15?.isNotEmpty() == true)
                items.add(DetailListItem("- ${it.strIngredient15} (${it.strMeasure15})"))

            if (it.strIngredient16?.isNotEmpty() == true)
                items.add(DetailListItem("- ${it.strIngredient16} (${it.strMeasure16})"))

            if (it.strIngredient17?.isNotEmpty() == true)
                items.add(DetailListItem("- ${it.strIngredient17} (${it.strMeasure17})"))

            if (it.strIngredient18?.isNotEmpty() == true)
                items.add(DetailListItem("- ${it.strIngredient18} (${it.strMeasure18})"))

            if (it.strIngredient19?.isNotEmpty() == true)
                items.add(DetailListItem("- ${it.strIngredient19} (${it.strMeasure19})"))

            if (it.strIngredient20?.isNotEmpty() == true)
                items.add(DetailListItem("- ${it.strIngredient20} (${it.strMeasure20})"))
            // END OF INGREDIENTS

            items.add(DetailListItem("\nHow to:"))
            items.add(DetailListItem(it.strInstructions ?: "No Instruction"))
            items.add(AdditionalSpaceItem())
            items.add(AdditionalSpaceItem())
            items.add(AdditionalSpaceItem())
        }
        return items
    }

    fun getMealFromCategory(idMeal: String) {
        _detailItem.value = Resource.loading(null)
        var temp: AllMealItem? = null
        val mainJob = viewModelScope.launch {
            val job = async { allMealItemRepository.getAllDataById(idMeal) }
            temp = job.await()
        }

        viewModelScope.launch {
            mainJob.join()
            temp?.let { _detailItem.value = mapFoodById(it) }
                ?: run { getFoodById(idMeal) }
        }
    }

    private fun getFoodById(idMeal: String) {
        detailRepository.getMealById(idMeal)
            .subscribeOn(Schedulers.io())
            .observeOn(Schedulers.io())
            .map { mapRandomFoodItem(it) }
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                _detailItem.value = it
                saveMealByFilterItemToDB()
            }, {
                _detailItem.value = Resource.error(it.message ?: "Unknown error", null)
                it.printStackTrace()
            }).addToDisposable()
    }

    private fun mapFoodById(it: AllMealItem): Resource<List<BaseItem>> {
        val items: MutableList<BaseItem> = mutableListOf()
        mealItemResponse = it.toMealItemResponse()
        idMeal = it.idMeal
        itemSource = it.strSource
        itemYoutubeSource = it.strYoutube
        items.add(DetailImageItem(it.strMealThumb ?: ""))
        items.add(
            FoodTitleItem(
                it.strMeal ?: "Delicious",
                it.strCategory ?: "Category",
                additionalInfo = "tags: ${it.strTags ?: "No Tag"}"
            ).setContainerStyle(viewGroupStyle {
                layoutMarginBottomDp(23)
            })
        )
        items.add(DetailListItem("Ingredients:"))

        // INGREDIENTS
        if (it.strIngredient1?.isNotEmpty() == true)
            items.add(DetailListItem("- ${it.strIngredient1} (${it.strMeasure1})"))

        if (it.strIngredient2?.isNotEmpty() == true)
            items.add(DetailListItem("- ${it.strIngredient2} (${it.strMeasure2})"))

        if (it.strIngredient3?.isNotEmpty() == true)
            items.add(DetailListItem("- ${it.strIngredient3} (${it.strMeasure3})"))

        if (it.strIngredient4?.isNotEmpty() == true)
            items.add(DetailListItem("- ${it.strIngredient4} (${it.strMeasure4})"))

        if (it.strIngredient5?.isNotEmpty() == true)
            items.add(DetailListItem("- ${it.strIngredient5} (${it.strMeasure5})"))

        if (it.strIngredient6?.isNotEmpty() == true)
            items.add(DetailListItem("- ${it.strIngredient6} (${it.strMeasure6})"))

        if (it.strIngredient7?.isNotEmpty() == true)
            items.add(DetailListItem("- ${it.strIngredient7} (${it.strMeasure7})"))

        if (it.strIngredient8?.isNotEmpty() == true)
            items.add(DetailListItem("- ${it.strIngredient8} (${it.strMeasure8})"))

        if (it.strIngredient9?.isNotEmpty() == true)
            items.add(DetailListItem("- ${it.strIngredient9} (${it.strMeasure9})"))

        if (it.strIngredient10?.isNotEmpty() == true)
            items.add(DetailListItem("- ${it.strIngredient10} (${it.strMeasure10})"))

        if (it.strIngredient11?.isNotEmpty() == true)
            items.add(DetailListItem("- ${it.strIngredient11} (${it.strMeasure11})"))

        if (it.strIngredient12?.isNotEmpty() == true)
            items.add(DetailListItem("- ${it.strIngredient12} (${it.strMeasure12})"))

        if (it.strIngredient13?.isNotEmpty() == true)
            items.add(DetailListItem("- ${it.strIngredient13} (${it.strMeasure13})"))

        if (it.strIngredient14?.isNotEmpty() == true)
            items.add(DetailListItem("- ${it.strIngredient14} (${it.strMeasure14})"))

        if (it.strIngredient15?.isNotEmpty() == true)
            items.add(DetailListItem("- ${it.strIngredient15} (${it.strMeasure15})"))

        if (it.strIngredient16?.isNotEmpty() == true)
            items.add(DetailListItem("- ${it.strIngredient16} (${it.strMeasure16})"))

        if (it.strIngredient17?.isNotEmpty() == true)
            items.add(DetailListItem("- ${it.strIngredient17} (${it.strMeasure17})"))

        if (it.strIngredient18?.isNotEmpty() == true)
            items.add(DetailListItem("- ${it.strIngredient18} (${it.strMeasure18})"))

        if (it.strIngredient19?.isNotEmpty() == true)
            items.add(DetailListItem("- ${it.strIngredient19} (${it.strMeasure19})"))

        if (it.strIngredient20?.isNotEmpty() == true)
            items.add(DetailListItem("- ${it.strIngredient20} (${it.strMeasure20})"))
        // END OF INGREDIENTS

        items.add(DetailListItem("\nHow to:"))
        items.add(DetailListItem(it.strInstructions ?: "No Instruction"))
        items.add(AdditionalSpaceItem())
        items.add(AdditionalSpaceItem())
        items.add(AdditionalSpaceItem())

        return if (items.isNotEmpty()) Resource.success(items)
        else Resource.empty(listOf(NoDataItem()))
    }

    private fun saveMealByFilterItemToDB() {
        if (currentItemList.isNotEmpty()) {
            viewModelScope.launch {
                allMealItemRepository.insertMeal(currentItemList.toMutableList())
            }
        }
    }

    fun getRandomFood() {
        _detailItem.value = Resource.loading(null)
        detailRepository.getRandomFood()
            .subscribeOn(Schedulers.io())
            .observeOn(Schedulers.io())
            .map { mapRandomFoodItem(it) }
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                _detailItem.value = it
            }, {
                _detailItem.value = Resource.error(it.message ?: "Unknown error", null)
                it.printStackTrace()
            }).addToDisposable()
    }

    private fun mapRandomFoodItem(response: Response<MealBaseResponse>?): Resource<List<BaseItem>> {
        val items: MutableList<BaseItem> = mutableListOf()
        response?.let { resp ->
            if (resp.isSuccessful) {
                currentItemList.clear()
                resp.body()?.meals?.forEach {
                    mealItemResponse = it
                    idMeal = it.idMeal
                    itemSource = it.strSource
                    itemYoutubeSource = it.strYoutube

                    currentItemList.add(it.toAllMealItem())

                    items.add(DetailImageItem(it.strMealThumb ?: ""))
                    items.add(
                        FoodTitleItem(
                            it.strMeal ?: "Delicious",
                            it.strCategory ?: "Category",
                            additionalInfo = "tags: ${it.strTags ?: "No Tag"}"
                        ).setContainerStyle(viewGroupStyle {
                            layoutMarginBottomDp(23)
                        })
                    )
                    items.add(DetailListItem("Ingredients:"))

                    // INGREDIENTS
                    if (it.strIngredient1?.isNotEmpty() == true)
                        items.add(DetailListItem("- ${it.strIngredient1} (${it.strMeasure1})"))

                    if (it.strIngredient2?.isNotEmpty() == true)
                        items.add(DetailListItem("- ${it.strIngredient2} (${it.strMeasure2})"))

                    if (it.strIngredient3?.isNotEmpty() == true)
                        items.add(DetailListItem("- ${it.strIngredient3} (${it.strMeasure3})"))

                    if (it.strIngredient4?.isNotEmpty() == true)
                        items.add(DetailListItem("- ${it.strIngredient4} (${it.strMeasure4})"))

                    if (it.strIngredient5?.isNotEmpty() == true)
                        items.add(DetailListItem("- ${it.strIngredient5} (${it.strMeasure5})"))

                    if (it.strIngredient6?.isNotEmpty() == true)
                        items.add(DetailListItem("- ${it.strIngredient6} (${it.strMeasure6})"))

                    if (it.strIngredient7?.isNotEmpty() == true)
                        items.add(DetailListItem("- ${it.strIngredient7} (${it.strMeasure7})"))

                    if (it.strIngredient8?.isNotEmpty() == true)
                        items.add(DetailListItem("- ${it.strIngredient8} (${it.strMeasure8})"))

                    if (it.strIngredient9?.isNotEmpty() == true)
                        items.add(DetailListItem("- ${it.strIngredient9} (${it.strMeasure9})"))

                    if (it.strIngredient10?.isNotEmpty() == true)
                        items.add(DetailListItem("- ${it.strIngredient10} (${it.strMeasure10})"))

                    if (it.strIngredient11?.isNotEmpty() == true)
                        items.add(DetailListItem("- ${it.strIngredient11} (${it.strMeasure11})"))

                    if (it.strIngredient12?.isNotEmpty() == true)
                        items.add(DetailListItem("- ${it.strIngredient12} (${it.strMeasure12})"))

                    if (it.strIngredient13?.isNotEmpty() == true)
                        items.add(DetailListItem("- ${it.strIngredient13} (${it.strMeasure13})"))

                    if (it.strIngredient14?.isNotEmpty() == true)
                        items.add(DetailListItem("- ${it.strIngredient14} (${it.strMeasure14})"))

                    if (it.strIngredient15?.isNotEmpty() == true)
                        items.add(DetailListItem("- ${it.strIngredient15} (${it.strMeasure15})"))

                    if (it.strIngredient16?.isNotEmpty() == true)
                        items.add(DetailListItem("- ${it.strIngredient16} (${it.strMeasure16})"))

                    if (it.strIngredient17?.isNotEmpty() == true)
                        items.add(DetailListItem("- ${it.strIngredient17} (${it.strMeasure17})"))

                    if (it.strIngredient18?.isNotEmpty() == true)
                        items.add(DetailListItem("- ${it.strIngredient18} (${it.strMeasure18})"))

                    if (it.strIngredient19?.isNotEmpty() == true)
                        items.add(DetailListItem("- ${it.strIngredient19} (${it.strMeasure19})"))

                    if (it.strIngredient20?.isNotEmpty() == true)
                        items.add(DetailListItem("- ${it.strIngredient20} (${it.strMeasure20})"))
                    // END OF INGREDIENTS

                    items.add(DetailListItem("\nHow to:"))
                    items.add(DetailListItem(it.strInstructions ?: "No Instruction"))
                    items.add(AdditionalSpaceItem())
                    items.add(AdditionalSpaceItem())
                    items.add(AdditionalSpaceItem())
                    return Resource.success(items)
                } ?: run {
                    return Resource.empty(listOf(NoDataItem()))
                }
            } else {
                val msg = response.errorBody()?.string()
                val errorMsg = if (msg.isNullOrEmpty()) response.message() else msg
                return Resource.error(errorMsg, null)
            }
        }
        return Resource.empty(listOf(NoDataItem()))
    }

    fun setFavorite() {
        val favorite = mealItemResponse.toFavorite()
        favorite?.let {
            viewModelScope.launch {
                favoriteRepository.insertFavorite(it)
            }
        }
    }

    fun deleteFavorite(idMeal: String) {
        viewModelScope.launch {
            favoriteRepository.deleteFavoriteById(idMeal)
        }
    }

    fun getIdMeal() = idMeal

    fun getItemSource() = itemSource

    fun getItemYoutubeSource() = itemYoutubeSource
}