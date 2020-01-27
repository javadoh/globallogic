package com.globallogic.imageloader.di

import com.bumptech.glide.Glide
import com.globallogic.imageloader.utils.IImageLoader
import com.globallogic.imageloader.utils.ImageLoaderImpl
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val imageLoaderModule = module {
    single<IImageLoader> {
        ImageLoaderImpl(Glide.with(androidContext()))
    }
}