package com.example.transformmonitorapp.data.repository.impl

import android.content.Context
import com.example.transformmonitorapp.data.network.ApiServiceProvider
import com.example.transformmonitorapp.data.repository.interfaces.AdminRepository

class AdminRepositoryImpl(context: Context) : AdminRepository{
    private val api = ApiServiceProvider.adminApi(context)
}