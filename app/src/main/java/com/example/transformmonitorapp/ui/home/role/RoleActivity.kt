package com.example.transformmonitorapp.ui.home.role

import GenericViewModelFactory
import android.annotation.SuppressLint
import android.graphics.Typeface
import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import com.example.transformmonitorapp.R
import com.example.transformmonitorapp.data.repository.impl.AuthRepositoryImpl
import com.example.transformmonitorapp.databinding.LayoutRoleWithDrawerBinding
import com.example.transformmonitorapp.views.models.HomeViewModel
import kotlin.getValue

abstract class RoleActivity : AppCompatActivity() {

    private lateinit var binding: LayoutRoleWithDrawerBinding

    val homeViewModel: HomeViewModel by viewModels {
        GenericViewModelFactory {
            HomeViewModel(application, AuthRepositoryImpl(applicationContext))
        }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = LayoutRoleWithDrawerBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initToolbar()
        setupAsideMenu()
        setupContentArea()
        customizeAsideMenu()
    }

    protected abstract fun customizeAsideMenu()

    protected val drawerLayout get() = binding.drawerLayout
    protected val header get() = binding.includeBaseRole
    protected val menuContainer get() = binding.roleMenuContainer

    protected val profileButton get() = binding.navProfile
    protected val logoutButton get() = binding.navLogoutAside
    protected val contentContainer get() = binding.contentContainer

    protected fun setContentLayout(layoutId: Int) {
        contentContainer.removeAllViews()
        layoutInflater.inflate(layoutId, contentContainer, true)
    }

    @SuppressLint("ResourceAsColor")
    private fun initToolbar() {
        header.tvTitle.apply {
            setText(R.string.profile)
            textSize = 24f
            setTextColor(getColor(R.color.primaryColor))
            setTypeface(typeface, Typeface.BOLD)
        }

        header.btnMenu.setOnClickListener {
            drawerLayout.openDrawer(GravityCompat.START)
        }

        profileButton.applyBoldTextColor(R.color.textColor)
        logoutButton.applyBoldTextColor(R.color.textColor)
    }
    private fun TextView.applyBoldTextColor(colorRes: Int) {
        setTextColor(resources.getColor(colorRes))
        setTypeface(typeface, Typeface.BOLD)
    }

    private fun setupAsideMenu() {
        profileButton.setOnClickListener {
            navigateToProfile()
            drawerLayout.closeDrawer(GravityCompat.START)
        }
        logoutButton.setOnClickListener {
            performLogout()
        }
    }
    private fun performLogout() {
        val token = homeViewModel.loadToken()
        if (token != null) {
            homeViewModel.logout(token) {
                runOnUiThread {
                    Toast.makeText(this, getString(R.string.logged_out_success), Toast.LENGTH_SHORT).show()
                    finishAffinity()
                    finish()
                }
            }
        } else {
            Toast.makeText(this, getString(R.string.token_missing), Toast.LENGTH_SHORT).show()
        }
    }

    protected abstract fun navigateToProfile()

    private fun setupContentArea() {
        val layoutId = getLayoutId()
        if (layoutId != 0) setContentLayout(layoutId)
    }

    protected open fun getLayoutId(): Int = 0
}
