package com.globallogic.homelist.local.source

import com.globallogic.homelist.data.model.HomeListData
import com.globallogic.homelist.data.repository.IHomeListLocalDataSource
import com.globallogic.homelist.local.database.HomeListDAO
import com.globallogic.homelist.local.mapper.IHomeListLocalMapper
import com.globallogic.homelist.local.model.HomeListLocalData
import io.reactivex.Completable
import io.reactivex.Observable

class HomeListLocalDataSourceImpl(
    private val homeListDataLocalMapper: IHomeListLocalMapper<HomeListData, HomeListLocalData>,
    private val homeListDAO: HomeListDAO
) : IHomeListLocalDataSource {

    override fun getHomeList(): Observable<List<HomeListData?>> {
        return homeListDAO.getHomeList()
            .map { localFeed ->
                localFeed.map { homeListDataLocalMapper.mapFrom(it) }
            }
    }

    override fun saveHomeListItem(feed: List<HomeListData?>) {
        feed.map { feedArticleData ->
            homeListDataLocalMapper.mapTo(feedArticleData)?.let { homeListDAO.saveHomeListItem(it) }
        }
    }

    override fun removeHomeListItem(id: String): Completable {
        return homeListDAO.removeHomeListItem(id)
    }

    override fun clearHomeList(): Completable {
        return homeListDAO.clearHomeList()
    }
}