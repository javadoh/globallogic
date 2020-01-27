package com.globallogic.homelist.remote.mapper

import com.globallogic.homelist.data.model.HomeListData
import com.globallogic.homelist.remote.model.HomeListModelApi

internal class HomeListDataNetworkMapper :
    HomeListRemoteMapper<HomeListData, HomeListModelApi> {
    override fun mapTo(input: HomeListData?): HomeListModelApi? {
        return input?.let {
                HomeListModelApi(
                input.title,
                input.description,
                input.image
            )
        }
    }

    override fun mapFrom(input: HomeListModelApi?): HomeListData? {
        return input?.let {
            HomeListData(
                input.title,
                input.description,
                input.image
            )
        }
    }

}