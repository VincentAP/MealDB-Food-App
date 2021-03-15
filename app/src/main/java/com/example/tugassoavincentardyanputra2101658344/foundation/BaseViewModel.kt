package com.example.tugassoavincentardyanputra2101658344.foundation

import androidx.lifecycle.ViewModel
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.disposables.Disposable
import retrofit2.Response

open class BaseViewModel : ViewModel() {

    private var compositeDisposable: CompositeDisposable? = null

    fun Disposable.addToDisposable() {
        compositeDisposable?.add(this)
            ?: run {
                compositeDisposable = CompositeDisposable()
                compositeDisposable?.add(this)
            }
    }

    fun <T> getErrorMessage(response: Response<T>): String {
        val msg = response.errorBody()?.string()
        val errorMsg = if (msg.isNullOrEmpty()) response.message() else msg
        return errorMsg ?: "unknown error"
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable?.clear()
    }
}