package com.example.transformmonitorapp.data.repository.impl

import com.example.transformmonitorapp.data.network.api.HomeApi
import com.example.transformmonitorapp.data.repository.interfaces.HomeRepository

class HomeRepositoryImpl (private val  api : HomeApi) : HomeRepository {
    override suspend fun getHome(token : String) =
        api.getHome("Bearer $token")
}