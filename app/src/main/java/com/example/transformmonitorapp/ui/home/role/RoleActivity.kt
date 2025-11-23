package com.example.transformmonitorapp.ui.home.role

import android.os.Bundle
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.edit
import com.example.transformmonitorapp.R
import com.example.transformmonitorapp.data.repository.impl.AuthRepositoryImpl
import com.example.transformmonitorapp.views.factories.HomeViewModelFactory
import com.example.transformmonitorapp.views.models.HomeViewModel
import kotlin.getValue

abstract class RoleActivity : AppCompatActivity() {

    private val homeViewModel: HomeViewModel by viewModels {
        HomeViewModelFactory(application, AuthRepositoryImpl())
    }
    protected lateinit var roleContainer: LinearLayout
    protected lateinit var tvUserName: TextView
    protected lateinit var btnLogout: Button


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.layout_base_role)

        tvUserName = findViewById(R.id.tvUserName)
        roleContainer = findViewById(R.id.roleContentContainer)
        btnLogout = findViewById(R.id.btnLogout)

        setupUI()
        observeViewModel()
        loadData()

        btnLogout.setOnClickListener {
            val token = getSharedPreferences("app_prefs", MODE_PRIVATE)
                .getString("JWT_TOKEN", null)

            if (token != null) {
                homeViewModel.logout(token)
            } else {
                Toast.makeText(this, "No token found", Toast.LENGTH_SHORT).show()
            }
        }

        homeViewModel.logoutResponse.observe(this) { response ->
            if (response?.isSuccessful == true) {
                getSharedPreferences("app_prefs", MODE_PRIVATE).edit { clear() }
                Toast.makeText(this, "Logged out successfully", Toast.LENGTH_SHORT).show()
                finish()
            } else {
                Toast.makeText(this, "Logout failed", Toast.LENGTH_SHORT).show()
            }
        }
    }

    /** Встановлення початкового UI, текстів тощо */
    protected open fun setupUI() {
        tvUserName.text = "Привіт, користувач!"
    }

    /** Підписка на LiveData ViewModel */
    protected open fun observeViewModel() {}

    /** Завантаження даних через ViewModel */
    protected open fun loadData() {}

    protected fun showError(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}