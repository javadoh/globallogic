package com.globallogic.laptopslist

import android.app.Application
import com.globallogic.homelist.di.homeListModule
import com.globallogic.imageloader.di.imageLoaderModule
import com.globallogic.itemdetail.di.detailModule
import com.globallogic.laptopslist.di.appModule
import com.globallogic.networking.di.networkModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class Application : Application() {
    override fun onCreate() {
        super.onCreate()

        startKoin {
            // use AndroidLogger as Koin Logger - default Level.INFO
            androidLogger()

            // Android context
            androidContext(this@Application)

            // modules
            modules(
                listOf(
                    appModule,
                    homeListModule,
                    detailModule,
                    networkModule,
                    imageLoaderModule
                )
            )
        }
    }
}