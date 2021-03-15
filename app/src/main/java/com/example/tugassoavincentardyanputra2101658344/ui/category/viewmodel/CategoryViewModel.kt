package com.example.tugassoavincentardyanputra2101658344.ui.category.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.viewModelScope
import com.airbnb.paris.extensions.layoutMarginLeftDp
import com.airbnb.paris.extensions.viewGroupStyle
import com.example.tugassoavincentardyanputra2101658344.api.model.MealBaseResponse
import com.example.tugassoavincentardyanputra2101658344.api.model.MealByFilterItemResponse
import com.example.tugassoavincentardyanputra2101658344.api.model.MealByFilterResponse
import com.example.tugassoavincentardyanputra2101658344.api.model.MealItemResponse
import com.example.tugassoavincentardyanputra2101658344.common.viewholder.AdditionalSpaceItem
import com.example.tugassoavincentardyanputra2101658344.common.viewholder.ListItem
import com.example.tugassoavincentardyanputra2101658344.common.viewholder.NoDataItem
import com.example.tugassoavincentardyanputra2101658344.db.entity.AllMealByFilter
import com.example.tugassoavincentardyanputra2101658344.db.entity.Category
import com.example.tugassoavincentardyanputra2101658344.foundation.BaseViewModel
import com.example.tugassoavincentardyanputra2101658344.repository.AllMealItemRepository
import com.example.tugassoavincentardyanputra2101658344.repository.CategoryRepository
import com.example.tugassoavincentardyanputra2101658344.ui.homepage.viewholder.FeatureCategoryItem
import com.example.tugassoavincentardyanputra2101658344.ui.homepage.viewmodel.HomepageViewModel
import com.example.tugassoavincentardyanputra2101658344.util.BaseItem
import com.example.tugassoavincentardyanputra2101658344.util.Resource
import com.example.tugassoavincentardyanputra2101658344.util.Status
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers
import io.reactivex.rxjava3.subjects.PublishSubject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import retrofit2.Response
import java.util.concurrent.TimeUnit
import javax.inject.Inject

@HiltViewModel
class CategoryViewModel @Inject constructor(
    private val categoryRepository: CategoryRepository,
    private val allMealItemRepository: AllMealItemRepository
) : BaseViewModel(), HomepageViewModel {

    private val _refresh: MutableLiveData<Boolean> = MutableLiveData()
    private val _mealItemList: MutableLiveData<Resource<List<BaseItem>>> = MutableLiveData()

    val homepageCategory: LiveData<Resource<List<FeatureCategoryItem>>> = Transformations
        .switchMap(_refresh) { getHomepageCategoryItem() }

    val mealItemList: MutableLiveData<Resource<List<BaseItem>>> = _mealItemList

    private val itemList: MutableList<BaseItem> = mutableListOf()
    private val searchPublishSubject = PublishSubject.create<String>()
    private val currentItemList: MutableList<AllMealByFilter> = mutableListOf()
    private var currentCategory = ""

    fun onInputStateChanged(query: String) {
        searchPublishSubject.onNext(query)
    }

    private fun getHomepageCategoryItem(): LiveData<Resource<List<FeatureCategoryItem>>> {
        val itemFromDB = categoryRepository.loadHomepageCategories()
        val item: MutableList<FeatureCategoryItem> = mutableListOf()
        return Transformations.map(itemFromDB) {
            when (it.status) {
                Status.LOADING -> Resource.loading(item)
                Status.SUCCESS -> {
                    item.clear()
                    (it.data as List<Category>).forEachIndexed { idx, categoryItem ->
                        if (idx == 0) {
                            item.add(
                                FeatureCategoryItem(
                                    categoryItem.imageUrl ?: "", categoryItem.categoryName
                                )
                                    .setContainerStyle(viewGroupStyle {
                                        layoutMarginLeftDp(23)
                                    })
                            )
                        } else item.add(
                            FeatureCategoryItem(
                                categoryItem.imageUrl ?: "",
                                categoryItem.categoryName
                            )
                        )
                    }
                    Resource.success(item)
                }
                Status.ERROR -> Resource.error(it.message ?: "ERROR", item)
                else -> Resource.empty(null)
            }
        }
    }

    // GET BY CATEGORY =============================================
    fun getMealByCategory(category: String) {
        currentCategory = category
        _mealItemList.value = Resource.loading(null)
        var temp: List<AllMealByFilter> = listOf()
        val mainJob = viewModelScope.launch {
            val job = async { allMealItemRepository.getMealByCategoryFromDB(category) }
            temp = job.await()
        }

        viewModelScope.launch {
            mainJob.join()
            if (temp.isNotEmpty()) {
                itemList.clear()
                val x = mapCategoryItem(temp)
                itemList.addAll(x.data ?: listOf(NoDataItem()))
                _mealItemList.value = x
            } else getMealByCategoryFromNetwork(category)
        }
    }

    private fun getMealByCategoryFromNetwork(category: String) {
        allMealItemRepository.getMealByCategory(category)
            .subscribeOn(Schedulers.io())
            .observeOn(Schedulers.io())
            .map {
                if (it.isSuccessful) {
                    it.body()?.meals?.let { itm ->
                        itemList.clear()
                        val x = mapCategoryItem(itm, category)
                        itemList.addAll(x.data ?: listOf(NoDataItem()))
                        x
                    }
                        ?: run { Resource.empty(listOf(NoDataItem())) }
                } else Resource.error(getErrorMessage(it), null)
            }
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                _mealItemList.value = it
                saveMealByFilterItemToDB()
            }, {
                _mealItemList.value = Resource.error(it.message ?: "Unknown error", null)
                it.printStackTrace()
            }).addToDisposable()
    }
    // BY CATEGORY =======================================================================

    // GET BY AREA =============================================
    fun getMealByArea(area: String) {
        _mealItemList.value = Resource.loading(null)
        var temp: List<AllMealByFilter> = listOf()
        val mainJob = viewModelScope.launch {
            val job = async { allMealItemRepository.getMealByAreaFromDB(area) }
            temp = job.await()
        }

        viewModelScope.launch {
            mainJob.join()
            if (temp.isNotEmpty()) {
                itemList.clear()
                val x = mapCategoryItem(temp)
                itemList.addAll(x.data ?: listOf(NoDataItem()))
                _mealItemList.value = x
            } else getMealByAreaFromNetwork(area)
        }
    }

    private fun getMealByAreaFromNetwork(area: String) {
        allMealItemRepository.getMealByArea(area)
            .subscribeOn(Schedulers.io())
            .observeOn(Schedulers.io())
            .map {
                if (it.isSuccessful) {
                    it.body()?.meals?.let { itm ->
                        itemList.clear()
                        val x = mapCategoryItem(itm)
                        itemList.addAll(x.data ?: listOf(NoDataItem()))
                        x
                    }
                        ?: run { Resource.empty(listOf(NoDataItem())) }
                } else Resource.error(getErrorMessage(it), null)
            }
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                _mealItemList.value = it
                saveMealByFilterItemToDB()
            }, {
                _mealItemList.value = Resource.error(it.message ?: "Unknown error", null)
                it.printStackTrace()
            }).addToDisposable()
    }
    // BY AREA =======================================================================

    // GET BY INGREDIENT =============================================
    fun getMealByIngredient(ingredient: String) {
        _mealItemList.value = Resource.loading(null)
        var temp: List<AllMealByFilter> = listOf()
        val mainJob = viewModelScope.launch {
            val job = async { allMealItemRepository.getMealByIngredientFromDB(ingredient) }
            temp = job.await()
        }

        viewModelScope.launch {
            mainJob.join()
            if (temp.isNotEmpty()) {
                itemList.clear()
                val x = mapCategoryItem(temp)
                itemList.addAll(x.data ?: listOf(NoDataItem()))
                _mealItemList.value = x
            } else getMealByIngredientFromNetwork(ingredient)
        }
    }

    private fun getMealByIngredientFromNetwork(ingredient: String) {
        allMealItemRepository.getMealByIngredient(ingredient)
            .subscribeOn(Schedulers.io())
            .observeOn(Schedulers.io())
            .map {
                if (it.isSuccessful) {
                    it.body()?.meals?.let { itm ->
                        itemList.clear()
                        val x = mapCategoryItem(itm)
                        itemList.addAll(x.data ?: listOf(NoDataItem()))
                        x
                    }
                        ?: run { Resource.empty(listOf(NoDataItem())) }
                } else Resource.error(getErrorMessage(it), null)
            }
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                _mealItemList.value = it
                saveMealByFilterItemToDB()
            }, {
                _mealItemList.value = Resource.error(it.message ?: "Unknown error", null)
                it.printStackTrace()
            }).addToDisposable()
    }
    // BY INGREDIENT =======================================================================

    private fun saveMealByFilterItemToDB() {
        if (currentItemList.isNotEmpty()) {
            viewModelScope.launch {
                allMealItemRepository.insertAllMealByFilter(currentItemList.toMutableList())
            }
        }
    }

    @Suppress("UNCHECKED_CAST")
    private fun <T> mapCategoryItem(
        item: List<T>,
        category: String = ""
    ): Resource<List<BaseItem>> {
        val items: MutableList<BaseItem> = mutableListOf()
        when (item[0]) {
            is AllMealByFilter -> {
                items.clear()
                (item as List<AllMealByFilter>).forEach {
                    items.add(
                        ListItem(
                            it.strMealThumb ?: "",
                            it.strMeal ?: "",
                            idMeal = it.idMeal,
                            isCategoryList = true
                        )
                    )
                }
            }
            is MealByFilterItemResponse -> {
                items.clear()
                currentItemList.clear()
                (item as List<MealByFilterItemResponse>).forEach {
                    currentItemList.add(
                        AllMealByFilter(
                            it.idMeal ?: "",
                            it.strMeal ?: "",
                            it.strMealThumb ?: "",
                            category = category,
                            area = null,
                            ingredient = ""
                        )
                    )
                    items.add(
                        ListItem(
                            it.strMealThumb ?: "",
                            it.strMeal ?: "",
                            idMeal = it.idMeal ?: "",
                            isCategoryList = true
                        )
                    )
                }
            }
            is MealItemResponse -> {
                items.clear()
                currentItemList.clear()
                (item as List<MealItemResponse>).forEach {
                    currentItemList.add(
                        AllMealByFilter(
                            it.idMeal,
                            it.strMeal ?: "",
                            it.strMealThumb ?: "",
                            category = it.strCategory,
                            area = it.strArea,
                            ingredient = getIngredientsAsString(it)
                        )
                    )
                    items.add(
                        ListItem(
                            it.strMealThumb ?: "",
                            it.strMeal ?: "",
                            idMeal = it.idMeal,
                            isCategoryList = true
                        )
                    )
                }
            }
        }
        return if (items.isNotEmpty()) {
            items.add(AdditionalSpaceItem())
            Resource.success(items)
        } else Resource.empty(listOf(NoDataItem()))
    }

    private fun getIngredientsAsString(it: MealItemResponse): String {
        val items: MutableList<String> = mutableListOf()
        if (it.strIngredient1?.isNotEmpty() == true) items.add(it.strIngredient1)
        if (it.strIngredient2?.isNotEmpty() == true) items.add(it.strIngredient2)
        if (it.strIngredient3?.isNotEmpty() == true) items.add(it.strIngredient3)
        if (it.strIngredient4?.isNotEmpty() == true) items.add(it.strIngredient4)
        if (it.strIngredient5?.isNotEmpty() == true) items.add(it.strIngredient5)
        if (it.strIngredient6?.isNotEmpty() == true) items.add(it.strIngredient6)
        if (it.strIngredient7?.isNotEmpty() == true) items.add(it.strIngredient7)
        if (it.strIngredient8?.isNotEmpty() == true) items.add(it.strIngredient8)
        if (it.strIngredient9?.isNotEmpty() == true) items.add(it.strIngredient9)
        if (it.strIngredient10?.isNotEmpty() == true) items.add(it.strIngredient10)
        if (it.strIngredient11?.isNotEmpty() == true) items.add(it.strIngredient11)
        if (it.strIngredient12?.isNotEmpty() == true) items.add(it.strIngredient12)
        if (it.strIngredient13?.isNotEmpty() == true) items.add(it.strIngredient13)
        if (it.strIngredient14?.isNotEmpty() == true) items.add(it.strIngredient14)
        if (it.strIngredient15?.isNotEmpty() == true) items.add(it.strIngredient15)
        if (it.strIngredient16?.isNotEmpty() == true) items.add(it.strIngredient16)
        if (it.strIngredient17?.isNotEmpty() == true) items.add(it.strIngredient17)
        if (it.strIngredient18?.isNotEmpty() == true) items.add(it.strIngredient18)
        if (it.strIngredient19?.isNotEmpty() == true) items.add(it.strIngredient19)
        if (it.strIngredient20?.isNotEmpty() == true) items.add(it.strIngredient20)
        return items.joinToString(" ") { it }
    }

    fun searchFood(input: String) {
        viewModelScope.launch(Dispatchers.Main) {
            _mealItemList.value = Resource.loading(null)
        }

        var temp: List<AllMealByFilter> = listOf()
        val mainJob = viewModelScope.launch {
            val job = async { allMealItemRepository.getMealBySearchFromDB(input) }
            temp = job.await()
        }

        viewModelScope.launch {
            mainJob.join()
            if (temp.isNotEmpty()) _mealItemList.value = mapCategoryItem(temp)
            else searchFoodFromNetwork()
        }
    }

    private fun searchFoodFromNetwork() {
        searchPublishSubject
            .debounce(1, TimeUnit.SECONDS)
            .distinctUntilChanged()
            .switchMap {
                if (it.isNotEmpty()) allMealItemRepository.getMealBySearch(it).toObservable()
                else allMealItemRepository.getMealByCategory(currentCategory).toObservable()
            }
            .subscribeOn(Schedulers.io())
            .observeOn(Schedulers.io())
            .map { mapSearchResult(it) }
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                _mealItemList.value = it
                saveMealByFilterItemToDB()
            }, {
                _mealItemList.value = Resource.error(it.message ?: "Unknown error", null)
                it.printStackTrace()
            }).addToDisposable()
    }

    private fun mapSearchResult(response: Response<out Any>?): Resource<List<BaseItem>> {
        val items: MutableList<BaseItem> = mutableListOf()
        response?.let { resp ->
            if (resp.isSuccessful) {
                resp.body()?.let {
                    when (it) {
                        is MealByFilterResponse -> {
                            items.clear()
                            currentItemList.clear()
                            (it.meals as List<MealByFilterItemResponse>).forEach { mealItm ->
                                currentItemList.add(
                                    AllMealByFilter(
                                        mealItm.idMeal ?: "",
                                        mealItm.strMeal ?: "",
                                        mealItm.strMealThumb ?: "",
                                        category = currentCategory,
                                        area = null,
                                        ingredient = ""
                                    )
                                )
                                items.add(
                                    ListItem(
                                        mealItm.strMealThumb ?: "",
                                        mealItm.strMeal ?: "",
                                        idMeal = mealItm.idMeal ?: "",
                                        isCategoryList = true
                                    )
                                )
                            }
                        }
                        is MealBaseResponse -> {
                            items.clear()
                            currentItemList.clear()
                            (it.meals as List<MealItemResponse>).forEach { mealItm ->
                                currentItemList.add(
                                    AllMealByFilter(
                                        mealItm.idMeal,
                                        mealItm.strMeal ?: "",
                                        mealItm.strMealThumb ?: "",
                                        category = currentCategory,
                                        area = null,
                                        ingredient = ""
                                    )
                                )
                                items.add(
                                    ListItem(
                                        mealItm.strMealThumb ?: "",
                                        mealItm.strMeal ?: "",
                                        idMeal = mealItm.idMeal,
                                        isCategoryList = true
                                    )
                                )
                            }
                        }
                    }
                } ?: run { return Resource.empty(listOf(NoDataItem())) }
            } else return Resource.error(getErrorMessage(resp), listOf(NoDataItem()))
        }
        return if (items.isNotEmpty()) {
            items.add(AdditionalSpaceItem())
            Resource.success(items)
        } else Resource.empty(listOf(NoDataItem()))
    }

    override fun refresh() {
        _refresh.value = true
    }
}