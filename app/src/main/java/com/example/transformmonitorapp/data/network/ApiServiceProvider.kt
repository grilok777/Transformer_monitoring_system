package com.example.transformmonitorapp.data.network

import android.content.Context
import com.example.transformmonitorapp.data.network.api.AdminApi
import com.example.transformmonitorapp.data.network.api.AnalystApi
import com.example.transformmonitorapp.data.network.api.AuthApi
import com.example.transformmonitorapp.data.network.api.CreatorApi
import com.example.transformmonitorapp.data.network.api.HomeApi
import com.example.transformmonitorapp.data.network.api.OperatorApi
import com.example.transformmonitorapp.data.network.api.ProfileApi

object ApiServiceProvider {

    fun authApi(context: Context): AuthApi =
        RetrofitClient.getClient(context).create(AuthApi::class.java)

    fun creatorApi(context: Context): CreatorApi =
        RetrofitClient.getClient(context).create(CreatorApi::class.java)

    fun adminApi(context: Context): AdminApi =
        RetrofitClient.getClient(context).create(AdminApi::class.java)

    fun operatorApi(context: Context): OperatorApi =
        RetrofitClient.getClient(context).create(OperatorApi::class.java)

    fun analystApi(context: Context): AnalystApi =
        RetrofitClient.getClient(context).create(AnalystApi::class.java)

    fun profileApi(context: Context): ProfileApi =
        RetrofitClient.getClient(context).create(ProfileApi::class.java)

}