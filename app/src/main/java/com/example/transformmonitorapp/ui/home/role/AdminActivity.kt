package com.example.transformmonitorapp.ui.home.role

import GenericViewModelFactory
import android.os.Bundle
import com.example.transformmonitorapp.views.models.AdminViewModel

import android.os.PersistableBundle
import androidx.activity.viewModels
import com.example.transformmonitorapp.data.network.ApiServiceProvider
import com.example.transformmonitorapp.data.repository.impl.AdminRepositoryImpl

class AdminActivity : RoleActivity() {

    private val viewModel: AdminViewModel by viewModels {
        GenericViewModelFactory {
            val repository = AdminRepositoryImpl(ApiServiceProvider.adminApi)

            AdminViewModel(application, repository)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        super.onCreate(savedInstanceState, persistentState)
    }

    override fun setupUI() {
        super.setupUI()
        tvUserName.text = "Привіт, Admin!"
    }

    override fun observeViewModel() {
    }

    override fun loadData() {
    }
}
