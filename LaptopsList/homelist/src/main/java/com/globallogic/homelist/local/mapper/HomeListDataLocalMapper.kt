package com.globallogic.homelist.local.mapper

import com.globallogic.homelist.data.model.HomeListData
import com.globallogic.homelist.local.model.HomeListLocalData

internal class HomeListDataLocalMapper :
    IHomeListLocalMapper<HomeListData, HomeListLocalData> {
    override fun mapTo(input: HomeListData?): HomeListLocalData? {
        return input?.let {
            HomeListLocalData(
                input.title,
                input.description,
                input.image
            )
        }
    }

    override fun mapFrom(input: HomeListLocalData?): HomeListData? {
        return input?.let {
            HomeListData(
                input.title,
                input.description,
                input.image
            )
        }
    }
}