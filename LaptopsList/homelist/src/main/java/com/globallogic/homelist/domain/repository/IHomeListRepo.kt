package com.globallogic.homelist.domain.repository

import com.globallogic.homelist.domain.entities.HomeListDataEntity
import io.reactivex.Completable
import io.reactivex.Observable

interface IHomeListRepo {
    fun getHomeList(): Observable<List<HomeListDataEntity?>>
    fun removeHomeListItem(id: String) : Completable
    fun clearHomeList(): Completable
}