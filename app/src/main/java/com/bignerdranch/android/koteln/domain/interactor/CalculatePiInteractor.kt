package com.bignerdranch.android.koteln.domain.interactor

import com.bignerdranch.android.koteln.domain.model.ResultCalculatePi
import io.reactivex.Single
import java.util.concurrent.Callable

class CalculatePiInteractor {


    fun calculate(iteration: Long): Single<ResultCalculatePi> {
        return Single.fromCallable(CalculateCallable(iteration))
    }

    private class CalculateCallable(val iteration: Long) : Callable<ResultCalculatePi> {
        override fun call(): ResultCalculatePi {
            var x: Double
            var y: Double
            var innerCount = 0

            for (i in 0 until iteration) {
                if (Thread.interrupted()) break

                x = Math.random() * 2 - 1
                y = Math.random() * 2 - 1
                if (Math.pow(x, 2.0) + Math.pow(y, 2.0) <= 1)
                    innerCount++

            }
            return ResultCalculatePi(innerCount, 4.0 * innerCount / iteration)
        }
    }
}