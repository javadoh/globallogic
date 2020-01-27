package com.globallogic.itemdetail.di

import com.globallogic.itemdetail.presentation.presenters.DetailPresenter
import com.globallogic.itemdetail.presentation.presenters.Presenter
import com.globallogic.itemdetail.ui.IDetailView
import org.koin.dsl.module

val detailModule = module {
    factory<Presenter<IDetailView>> { (view: IDetailView) ->
        DetailPresenter(view)
    }
}