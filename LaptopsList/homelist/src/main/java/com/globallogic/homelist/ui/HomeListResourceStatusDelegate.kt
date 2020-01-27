package com.globallogic.homelist.ui

import com.globallogic.homelist.presentation.model.HomeList
import com.globallogic.homelist.presentation.model.resource.Resource
import com.globallogic.homelist.presentation.model.resource.ResourceStatusDelegate

internal class HomeListResourceStatusDelegate(private val view: IHomeListView) :
        ResourceStatusDelegate<List<HomeList?>>() {

        override fun handleSuccess(resource: Resource<List<HomeList?>>) {
            view.showLoading(false)
            view.showError(false)
            view.showList(true, resource.data)
        }

        override fun handleError(resource: Resource<List<HomeList?>>) {
            view.showLoading(false)
            view.showList(false)
            view.showError(true, resource.message)
        }

        override fun handleLoading(resource: Resource<List<HomeList?>>) {
            view.showError(false)
            view.showList(false)
            view.showLoading(true)
        }
}