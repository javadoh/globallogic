package com.globallogic.laptopslist.di

import android.content.Context
import com.globallogic.homelist.IHomeListNavigation
import com.globallogic.laptopslist.Navigator
import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import org.koin.core.qualifier.named
import org.koin.dsl.module

val appModule = module {
    single(named("io")) { Schedulers.io() }

    single<Scheduler>(named("mainThread")) { AndroidSchedulers.mainThread() }

    single<IHomeListNavigation> { (context: Context) ->
        Navigator(context)
    }
}