package com.example.tugassoavincentardyanputra2101658344.extension

//fun <T> Observable<T>.toLiveData(backPressureStrategy: BackpressureStrategy) : LiveData<T> {
//    return LiveDataReactiveStreams.fromPublisher(this.toFlowable(backPressureStrategy))
//}
//
//fun <T> Single<T>.toLiveData() :  LiveData<T> {
//    return LiveDataReactiveStreams.fromPublisher(this.toFlowable().subscribeOn(Schedulers.io()))
//}

fun String.getCategory(): String {
    return this.substring(0, 4)
}