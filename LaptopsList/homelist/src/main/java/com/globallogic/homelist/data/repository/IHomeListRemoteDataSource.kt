package com.globallogic.homelist.data.repository

import com.globallogic.homelist.data.model.HomeListData
import io.reactivex.Observable

interface IHomeListRemoteDataSource {
    fun getHomeList(): Observable<List<HomeListData?>>
}