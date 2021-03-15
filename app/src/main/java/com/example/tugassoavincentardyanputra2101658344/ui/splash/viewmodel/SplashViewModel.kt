package com.example.tugassoavincentardyanputra2101658344.ui.splash.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.tugassoavincentardyanputra2101658344.db.entity.FilterType
import com.example.tugassoavincentardyanputra2101658344.foundation.BaseViewModel
import com.example.tugassoavincentardyanputra2101658344.repository.FilterTypeRepository
import com.example.tugassoavincentardyanputra2101658344.util.Resource
import com.example.tugassoavincentardyanputra2101658344.util.toFilterType
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import retrofit2.Response
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val filterTypeRepository: FilterTypeRepository
) : BaseViewModel() {

    private val _filterItem: MutableLiveData<Resource<List<FilterType>>> = MutableLiveData()
    val filterItem: LiveData<Resource<List<FilterType>>> = _filterItem

    private val itemList: MutableList<FilterType> = mutableListOf()

    fun getFilter() {
        itemList.clear()
        var temp: List<FilterType> = listOf()
        _filterItem.value = Resource.loading(null)

        val mainJob = viewModelScope.launch {
            val job = async { filterTypeRepository.getAllFilterType() }
            temp = job.await()
        }

        viewModelScope.launch {
            mainJob.join()
            if (temp.isNotEmpty()) {
                itemList.addAll(temp)
                _filterItem.value = Resource.success(itemList)
            } else getFilterFromNetwork()
        }
    }

    private fun getFilterFromNetwork() {
        filterTypeRepository.getFilter()
            .map { mapFilterItem(it) }
            .observeOn(AndroidSchedulers.mainThread())
            .doFinally {
                if (itemList.isNotEmpty()) _filterItem.value = Resource.success(itemList)
                else _filterItem.value = Resource.empty(null)
            }
            .subscribe({
                Timber.i("SUBSCRIBED")
            }, {
                it.printStackTrace()
                _filterItem.value = Resource.error("Unknown error", null)
            }).addToDisposable()
    }

    private fun mapFilterItem(response: Response<out Any>?) {
        response?.let {
            if (it.isSuccessful) {
                it.body()?.toFilterType()?.let { lst ->
                    if (lst.isNotEmpty()) lst.forEach { ft -> itemList.add(ft) }
                }
            }
        }
    }

    fun insertFilterType(filterType: List<FilterType>) {
        _filterItem.value = Resource.loading(null)
        val job = viewModelScope.launch {
            filterTypeRepository.insertFilterType(filterType)
        }

        viewModelScope.launch {
            job.join()
            _filterItem.value = Resource.success(null)
        }
    }
}