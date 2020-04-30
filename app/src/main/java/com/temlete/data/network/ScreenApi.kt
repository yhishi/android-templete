package com.temlete.data.network

import com.temlete.domain.entity.RandomUser
import io.reactivex.Single
import retrofit2.http.GET

interface ScreenApi {
    @GET("api")
    fun getRandomUser(): Single<RandomUser>

    @GET("api")
    fun getRandomUser2(): Single<RandomUser>
}