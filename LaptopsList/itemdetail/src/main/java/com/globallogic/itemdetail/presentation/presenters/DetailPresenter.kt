package com.globallogic.itemdetail.presentation.presenters

import com.globallogic.itemdetail.presentation.model.ItemDetail
import com.globallogic.itemdetail.ui.IDetailView

class DetailPresenter(private val view: IDetailView) :
    Presenter<IDetailView> {
    override fun startPresenting(article: ItemDetail) {
        view.showDetail(article)
    }
}