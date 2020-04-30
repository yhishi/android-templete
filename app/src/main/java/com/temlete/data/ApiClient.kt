package com.temlete.data

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.CallAdapter
import retrofit2.Converter
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit


abstract class ApiClient {

    abstract val baseUrl: String

    // Retrofitのインスタンス
    val retrofit: Retrofit by lazy {
        getBaseRetrofit()
            .baseUrl(baseUrl)
            .client(httpBuilder.build())
            .build()
            .let { requireNotNull(it) }
    }

    private val httpBuilder: OkHttpClient.Builder
        get() {
            val httpClient = OkHttpClient.Builder()
                .addInterceptor(Interceptor { chain ->
                    val original = chain.request()

                    // header
                    val request = original.newBuilder()
                        .header("Content-Type", "application/json; charset=utf-8")
                        .method(original.method(), original.body())
                        .build()

                    return@Interceptor chain.proceed(request)
                })
                .connectTimeout(15, TimeUnit.SECONDS)
                .readTimeout(20, TimeUnit.SECONDS)

            // log interceptor
            val loggingInterceptor = HttpLoggingInterceptor()
            loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
            httpClient.addInterceptor(loggingInterceptor)

            return httpClient
        }

    // Retrofit基本
    private fun getBaseRetrofit(): Retrofit.Builder = Retrofit.Builder()
        .addConverterFactory(getConverterFactory())
        .addCallAdapterFactory(geAdapterFactory())

    // ConverterFactory Moshi
    protected open fun getConverterFactory(): Converter.Factory = MoshiConverterFactory.create(
        Moshi.Builder()
            .add(KotlinJsonAdapterFactory())
            .build()
    )

    // AdapterFactory TODO 共通のエラー処理を書きたい
    protected open fun geAdapterFactory(): CallAdapter.Factory = RxJava2CallAdapterFactory.create()
}