package com.globallogic.homelist.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.toLiveData
import com.globallogic.homelist.domain.entities.HomeListDataEntity
import com.globallogic.homelist.domain.usecases.GetHomeListItemUseCase
import com.globallogic.homelist.domain.usecases.base.ObservableUseCase
import com.globallogic.homelist.presentation.mapper.IHomeListMapper
import com.globallogic.homelist.presentation.model.HomeList
import com.globallogic.homelist.presentation.model.resource.Resource
import io.reactivex.BackpressureStrategy
import io.reactivex.Observable
import io.reactivex.functions.Function

internal class HomeListVM(
    private val getHomeListUseCase: ObservableUseCase<List<HomeListDataEntity?>, GetHomeListItemUseCase.Params>,
    private val homeListDomainMapper: IHomeListMapper<HomeList?, HomeListDataEntity?>
) : ViewModel() {

    val items: LiveData<Resource<List<HomeList?>>>
        get() =
            getHomeListUseCase.buildUseCase(GetHomeListItemUseCase.Params())
                .map { homeListDomainMapper.mapFrom(it) }
                .map { Resource.success(it) }
                .startWith(Resource.loading(null))
                .onErrorResumeNext(
                    Function {
                        Observable.just(Resource.error(it.localizedMessage!!, null))
                    }
                )
                .toFlowable(BackpressureStrategy.LATEST)
                .toLiveData()
}