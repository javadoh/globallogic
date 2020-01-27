package com.globallogic.homelist.data.repository

import com.globallogic.homelist.data.mapper.IHomeListDomainMapper
import com.globallogic.homelist.data.model.HomeListData
import com.globallogic.homelist.domain.entities.HomeListDataEntity
import com.globallogic.homelist.domain.repository.IHomeListRepo
import io.reactivex.Completable
import io.reactivex.Observable

internal class HomeListRepoImpl(
    private val homeListDomainDataDomainMapper: IHomeListDomainMapper<HomeListDataEntity, HomeListData>,
    private val iLocalDataSource: IHomeListLocalDataSource,
    private val iRemoteDataSource: IHomeListRemoteDataSource
) : IHomeListRepo {

    override fun getHomeList(): Observable<List<HomeListDataEntity?>> {

        val localDataSourceObservable =
            iLocalDataSource.getHomeList()
                .map { result -> result.map { homeListDomainDataDomainMapper.mapFrom(it) } }
                .onErrorResumeNext(Observable.empty())

        return iRemoteDataSource.getHomeList()
            .map { result ->
                iLocalDataSource.saveHomeListItem(result)
                result.map { homeListDomainDataDomainMapper.mapFrom(it) }
            }.onErrorResumeNext(Observable.empty())
            .concatWith(localDataSourceObservable)
    }

    override fun removeHomeListItem(id: String): Completable {
        return iLocalDataSource.removeHomeListItem(id)
    }

    override fun clearHomeList(): Completable {
        return iLocalDataSource.clearHomeList()
    }

}