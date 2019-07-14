package com.bignerdranch.android.koteln.presentation.viewModel

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.bignerdranch.android.koteln.domain.interactor.CalculatePiInteractor
import com.bignerdranch.android.koteln.domain.model.ResultCalculatePi
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

class CalculatePiViewModel : ViewModel() {

    private val interactor = CalculatePiInteractor()
    val result = MutableLiveData<ResultCalculatePi>()
    private var iteration: Long = 0
    private var calculateDisposable: Disposable? = null

    val inProgress = MutableLiveData<Boolean>()


    fun calculate(iteration: Long) {
        this.iteration = iteration
        inProgress.value = true
        calculateDisposable = interactor.calculate(iteration)
                .subscribeOn(Schedulers.computation())
                .subscribe({
                    inProgress.postValue(false)
                    result.postValue(it)
                }, {
                    it.printStackTrace()
                })
    }

    fun cancelCalculate() {

        inProgress.value = false
        calculateDisposable?.dispose()

    }


    fun getIteration(): Long {
        return iteration
    }

}