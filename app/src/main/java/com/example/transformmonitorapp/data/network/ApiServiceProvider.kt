package com.example.transformmonitorapp.data.network

import com.example.transformmonitorapp.data.network.RetrofitClient.retrofit
import com.example.transformmonitorapp.data.network.api.AdminApi
import com.example.transformmonitorapp.data.network.api.AnalystApi
import com.example.transformmonitorapp.data.network.api.AuthApi
import com.example.transformmonitorapp.data.network.api.CreatorApi
import com.example.transformmonitorapp.data.network.api.HomeApi
import com.example.transformmonitorapp.data.network.api.OperatorApi

object ApiServiceProvider {
    val authApi: AuthApi by lazy {
        retrofit.create(AuthApi::class.java)
    }

    val creatorApi: CreatorApi by lazy {
        retrofit.create(CreatorApi::class.java)
    }

    val adminApi: AdminApi by lazy {
        retrofit.create(AdminApi::class.java)
    }

    val operatorApi: OperatorApi by lazy {
        retrofit.create(OperatorApi::class.java)
    }

    val dataAnalystApi: AnalystApi by lazy {
        retrofit.create(AnalystApi::class.java)
    }

    val homeApi : HomeApi by lazy {
        retrofit.create(HomeApi::class.java)
    }
}