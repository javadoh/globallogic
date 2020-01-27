package com.globallogic.homelist.remote.source

import com.globallogic.homelist.data.model.HomeListData
import com.globallogic.homelist.data.repository.IHomeListRemoteDataSource
import com.globallogic.homelist.remote.IHomeListService
import com.globallogic.homelist.remote.mapper.HomeListRemoteMapper
import com.globallogic.homelist.remote.model.HomeListModelApi
import io.reactivex.Observable

internal class HomeListRemoteDataSrcImpl(
    private val feedArticleDataNetworkMapper: HomeListRemoteMapper<HomeListData, HomeListModelApi>,
    private val homeListService: IHomeListService
) : IHomeListRemoteDataSource {

    override fun getHomeList(): Observable<List<HomeListData?>> {
        return homeListService.getItems()
            .map { response ->
                response.map {
                    feedArticleDataNetworkMapper.mapFrom(it)
                }
            }
    }
}