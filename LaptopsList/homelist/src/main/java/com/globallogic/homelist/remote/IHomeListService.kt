package com.globallogic.homelist.remote

import com.globallogic.homelist.remote.model.HomeListModelApi
import com.globallogic.homelist.remote.model.HomeListResponseApiModel
import io.reactivex.Observable
import retrofit2.http.GET

interface IHomeListService {
        @GET("list")
        fun getItems(): Observable<List<HomeListModelApi>>
    }