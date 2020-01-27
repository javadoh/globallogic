package com.globallogic.networking.di

import okhttp3.Interceptor
import okhttp3.OkHttpClient
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

val networkModule = module {
    single { OkHttpClient.Builder() }

    //SINGLETON DEL INTERCEPTOR OKHTTP
    single {
        Interceptor { chain ->
            val original = chain.request()
            val originalHttpUrl = original.url()

            val url = originalHttpUrl.newBuilder()
                //.addQueryParameter("apiKey", "<68a333436766446786b20a5dfca40cc7>") //todo the api key
                .build()

            // Request customization: add request headers
            val requestBuilder = original.newBuilder()
                .url(url)

            val request = requestBuilder.build()
            chain.proceed(request)
        }
    }

    //SINGLETON DE RETROFIT BUILDER
    single {
        Retrofit.Builder()
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl("http://private-f0eea-mobilegllatam.apiary-mock.com/")
            .client(
                get<OkHttpClient.Builder>()
                    .addInterceptor(get())
                    .build()
            )
            .build()
    }
}