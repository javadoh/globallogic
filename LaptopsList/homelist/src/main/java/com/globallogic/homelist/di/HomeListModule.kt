package com.globallogic.homelist.di

import com.globallogic.homelist.data.mapper.HomeListDomainDataMapper
import com.globallogic.homelist.data.mapper.IHomeListDomainMapper
import com.globallogic.homelist.data.model.HomeListData
import com.globallogic.homelist.data.repository.HomeListRepoImpl
import com.globallogic.homelist.data.repository.IHomeListLocalDataSource
import com.globallogic.homelist.data.repository.IHomeListRemoteDataSource
import com.globallogic.homelist.domain.entities.HomeListDataEntity
import com.globallogic.homelist.domain.repository.IHomeListRepo
import com.globallogic.homelist.domain.usecases.GetHomeListItemUseCase
import com.globallogic.homelist.domain.usecases.base.ObservableUseCase
import com.globallogic.homelist.local.HomeListDB
import com.globallogic.homelist.local.mapper.HomeListDataLocalMapper
import com.globallogic.homelist.local.mapper.IHomeListLocalMapper
import com.globallogic.homelist.local.model.HomeListLocalData
import com.globallogic.homelist.local.source.HomeListLocalDataSourceImpl
import com.globallogic.homelist.presentation.mapper.HomeListDomainItemsMapper
import com.globallogic.homelist.presentation.mapper.IHomeListMapper
import com.globallogic.homelist.presentation.model.HomeList
import com.globallogic.homelist.presentation.model.resource.ResourceStatusDelegate
import com.globallogic.homelist.presentation.viewmodel.HomeListVM
import com.globallogic.homelist.remote.IHomeListService
import com.globallogic.homelist.remote.mapper.HomeListDataNetworkMapper
import com.globallogic.homelist.remote.mapper.HomeListRemoteMapper
import com.globallogic.homelist.remote.model.HomeListModelApi
import com.globallogic.homelist.remote.source.HomeListRemoteDataSrcImpl
import com.globallogic.homelist.ui.HomeListResourceStatusDelegate
import com.globallogic.homelist.ui.IHomeListView
import com.globallogic.homelist.ui.adapters.HeadlinerViewBinder
import com.globallogic.homelist.ui.adapters.HomeListAdapter
import com.globallogic.homelist.ui.adapters.ItemViewBinder
import com.globallogic.homelist.ui.adapters.OnItemClickListener
import org.koin.android.ext.koin.androidApplication
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.core.parameter.parametersOf
import org.koin.core.qualifier.named
import org.koin.dsl.module
import retrofit2.Retrofit

val homeListModule = module {

    single<IHomeListService> {
        get<Retrofit>().create(IHomeListService::class.java)
    }

    single<IHomeListLocalDataSource> {
        HomeListLocalDataSourceImpl(
            get(named("HomeListDataLocalMapper")),
            get()
        )
    }

    single<IHomeListLocalMapper<HomeListData, HomeListLocalData>>(named("HomeListDataLocalMapper")) {
        HomeListDataLocalMapper()
    }

    factory<ResourceStatusDelegate<List<HomeList?>>> { (view: IHomeListView) ->
        HomeListResourceStatusDelegate(
            view
        )
    }

    single<IHomeListRemoteDataSource> {
        HomeListRemoteDataSrcImpl(
            get(named("HomeListDataNetworkMapper")),
            get()
        )
    }

    single<HomeListRemoteMapper<HomeListData, HomeListModelApi>>(named("HomeListDataNetworkMapper")) {
        HomeListDataNetworkMapper()
    }

    single(named("HomeListAdapter")) { (icl: OnItemClickListener, dataset: List<HomeList?>?) ->
        HomeListAdapter(get { parametersOf(icl) }, get { parametersOf(icl) }, dataset)
    }

    single { (onClick: OnItemClickListener) -> HeadlinerViewBinder(get(), onClick) }

    single { (onClick: OnItemClickListener) -> ItemViewBinder(get(), onClick) }

    single<IHomeListDomainMapper<HomeListDataEntity, HomeListData>>(named("HomeListDomainDataMapper")) {
        HomeListDomainDataMapper()
    }

    single<IHomeListRepo> {
        HomeListRepoImpl(
            get(named("HomeListDomainDataMapper")),
            get(),
            get()
        )
    }

    single<ObservableUseCase<List<HomeListDataEntity?>, GetHomeListItemUseCase.Params>>(named("GetHomeListItemUseCase")) {
        GetHomeListItemUseCase(
            get(),
            get(named("io")),
            get(named("mainThread"))
        )
    }

    viewModel {
        HomeListVM(
            get(named("GetHomeListItemUseCase")),
            get(named("HomeListDomainItemsMapper"))
        )
    }

    single<IHomeListMapper<HomeList?, HomeListDataEntity?>>(named("HomeListDomainItemsMapper")) {
        HomeListDomainItemsMapper()
    }

    single {
        HomeListDB.getInstance(androidApplication())
    }

    single { get<HomeListDB>().getHomeListDAO() }
}