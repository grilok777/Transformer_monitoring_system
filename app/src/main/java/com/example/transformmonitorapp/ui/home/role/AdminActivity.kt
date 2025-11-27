package com.example.transformmonitorapp.ui.home.role

import GenericViewModelFactory
import android.annotation.SuppressLint
import android.os.Bundle
import com.example.transformmonitorapp.views.models.AdminViewModel

import android.os.PersistableBundle
import androidx.activity.viewModels
import com.example.transformmonitorapp.data.network.ApiServiceProvider
import com.example.transformmonitorapp.data.repository.impl.AdminRepositoryImpl

class AdminActivity : RoleActivity() {

    private val viewModel: AdminViewModel by viewModels {
        GenericViewModelFactory {
            val repository = AdminRepositoryImpl(applicationContext)

            AdminViewModel(application, repository)
        }
    }

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        super.onCreate(savedInstanceState, persistentState)
    }

    override fun customizeAsideMenu() {
        TODO("Not yet implemented")
    }

    override fun navigateToProfile() {
        TODO("Not yet implemented")
    }
}
