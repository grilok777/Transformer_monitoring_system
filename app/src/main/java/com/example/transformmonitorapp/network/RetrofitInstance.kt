package com.example.transformmonitorapp.network

import com.example.transformmonitorapp.network.services.ApiService
import com.example.transformmonitorapp.network.services.ApiServiceAdmin
import com.example.transformmonitorapp.network.services.ApiServiceCreator
import com.example.transformmonitorapp.network.services.ApiServiceDataAnalyst
import com.example.transformmonitorapp.network.services.ApiServiceOperator
import com.example.transformmonitorapp.network.services.ApiServiceUser
import com.example.transformmonitorapp.network.services.HomeApi
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import com.example.transformmonitorapp.utils.Constants.BASE_URL
import kotlin.getValue


object RetrofitInstance {
    private val logging = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }

    private val client = OkHttpClient.Builder()
        .addInterceptor(logging)
        .build()

    private val retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    val authApi: ApiService by lazy {
        retrofit.create(ApiService::class.java)
    }

    val creatorApi: ApiServiceCreator by lazy {
        retrofit.create(ApiServiceCreator::class.java)
    }

    val adminApi: ApiServiceAdmin by lazy {
        retrofit.create(ApiServiceAdmin::class.java)
    }

    val operatorApi: ApiServiceOperator by lazy {
        retrofit.create(ApiServiceOperator::class.java)
    }

    val dataAnalystApi: ApiServiceDataAnalyst by lazy {
        retrofit.create(ApiServiceDataAnalyst::class.java)
    }

    val userApi: ApiServiceUser by lazy {
        retrofit.create(ApiServiceUser::class.java)
    }

    val homeApi : HomeApi by lazy {
        retrofit.create(HomeApi::class.java)
    }
}