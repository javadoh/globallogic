package com.globallogic.homelist.domain.usecases

import com.globallogic.homelist.domain.repository.IHomeListRepo
import com.globallogic.homelist.domain.entities.HomeListDataEntity
import com.globallogic.homelist.domain.usecases.base.ObservableUseCase
import io.reactivex.Observable
import io.reactivex.Scheduler

class GetHomeListItemUseCase(
    private val iHomeListRepo: IHomeListRepo,
    backgroundScheduler: Scheduler,
    foregroundScheduler: Scheduler) : ObservableUseCase<List<HomeListDataEntity?>, GetHomeListItemUseCase.Params>(
    backgroundScheduler,
    foregroundScheduler
) {

    override fun generateObservable(input: Params?): Observable<List<HomeListDataEntity?>> {
        return input?.let { iHomeListRepo.getHomeList() }
            ?: throw IllegalArgumentException("The input parameters cannot be null")
    }

    data class Params(val a : Boolean = true)

}