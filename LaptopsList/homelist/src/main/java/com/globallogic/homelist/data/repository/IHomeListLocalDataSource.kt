package com.globallogic.homelist.data.repository

import com.globallogic.homelist.data.model.HomeListData
import io.reactivex.Completable
import io.reactivex.Observable

interface IHomeListLocalDataSource {

        fun getHomeList(): Observable<List<HomeListData?>>
        fun saveHomeListItem(feed: List<HomeListData?>)
        fun clearHomeList(): Completable
        fun removeHomeListItem(id: String): Completable
}