package com.example.transformmonitorapp.ui.home.role

import GenericViewModelFactory
import android.annotation.SuppressLint
import android.graphics.Typeface
import android.os.Bundle
import android.widget.*
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import com.example.transformmonitorapp.R
import com.example.transformmonitorapp.data.repository.impl.AuthRepositoryImpl
import com.example.transformmonitorapp.data.repository.impl.ProfileRepositoryImpl
import com.example.transformmonitorapp.databinding.LayoutRoleWithDrawerBinding
import com.example.transformmonitorapp.domain.dto.UserDto
import com.example.transformmonitorapp.views.models.HomeViewModel
import com.example.transformmonitorapp.views.models.ProfileViewModel

abstract class RoleActivity : AppCompatActivity() {

    private lateinit var binding: LayoutRoleWithDrawerBinding

    val homeViewModel: HomeViewModel by viewModels {
        GenericViewModelFactory {
            HomeViewModel(application, AuthRepositoryImpl(applicationContext))
        }
    }

    private val profileViewModel: ProfileViewModel by viewModels {
        val token = homeViewModel.loadToken() ?: ""
        GenericViewModelFactory {
            ProfileViewModel(
                application,
                ProfileRepositoryImpl(applicationContext, token)
            )
        }
    }

    protected val drawerLayout get() = binding.drawerLayout
    protected val header get() = binding.includeBaseRole
    protected val menuContainer get() = binding.roleMenuContainer
    protected val profileButton get() = binding.navProfile
    protected val logoutButton get() = binding.navLogoutAside
    protected val contentContainer get() = binding.contentContainer

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
    protected open fun getLayoutId(): Int = 0

    fun navigateToProfile() {
        setContentLayout(R.layout.layout_profile)
        header.tvTitle.setText(R.string.profile)
        setupProfileView()
    }
    protected fun setContentLayout(layoutId: Int) {
        contentContainer.removeAllViews()
        layoutInflater.inflate(layoutId, contentContainer, true)
    }

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
        setTextColor(colorRes)
        setTypeface(typeface, Typeface.BOLD)
    }

    private fun setupAsideMenu() {
        profileButton.setOnClickListener {
            navigateToProfile()
            drawerLayout.closeDrawer(GravityCompat.START)
        }

        logoutButton.setOnClickListener { performLogout() }
    }

    private fun performLogout() {
        val token = homeViewModel.loadToken()
        if (token != null) {
            homeViewModel.logout(token) {
                runOnUiThread {
                    Toast.makeText(this, getString(R.string.logged_out_success), Toast.LENGTH_SHORT).show()
                    finishAffinity()
                }
            }
        } else {
            Toast.makeText(this, getString(R.string.token_missing), Toast.LENGTH_SHORT).show()
        }
    }

    private fun setupContentArea() {
        val layoutId = getLayoutId()
        if (layoutId != 0) setContentLayout(layoutId)
    }

    protected fun getUser(): UserDto? = homeViewModel.loadUserFromPrefs()

    @SuppressLint("SetTextI18n")
    protected fun setupProfileView() {
        val profileTitle = findViewById<TextView>(R.id.tvProfileTitle)
        val etName = findViewById<EditText>(R.id.etName)
        val etEmail = findViewById<EditText>(R.id.etEmail)
        val etPassword = findViewById<EditText>(R.id.etPassword)
        val btnEdit = findViewById<Button>(R.id.btnEdit)
        val btnSave = findViewById<Button>(R.id.btnSave)

        val user = getUser() ?: return

        fun refreshUI(user: UserDto) {
            profileTitle.text = "Детально про ${user.nameUKR}"
            etName.hint = user.nameUKR
            etEmail.hint = user.email
            etPassword.hint = "******"
        }

        refreshUI(user)

        btnEdit.setOnClickListener {
            etName.isEnabled = true
            etEmail.isEnabled = true
            etPassword.isEnabled = true

            btnSave.visibility = Button.VISIBLE
            btnEdit.visibility = Button.GONE
        }

        btnSave.setOnClickListener {
            val newName = etName.text.toString().ifBlank { user.nameUKR }
            val newEmail = etEmail.text.toString().ifBlank { user.email }
            val newPassword = etPassword.text.toString()

            val updatedUser = user.copy(
                nameUKR = newName,
                email = newEmail,
                role = user.role
            )

            // Оновлення через ProfileViewModel
            val tasks = mutableListOf<() -> Unit>()

            if (newName != user.nameUKR) {
                tasks.add {
                    profileViewModel.changeName(user.id, newName) {
                        runOnUiThread { Toast.makeText(this, "Ім’я оновлено", Toast.LENGTH_SHORT).show() }
                    }
                }
            }

            if (newEmail != user.email) {
                tasks.add {
                    profileViewModel.changeEmail(user.id, newEmail) {
                        runOnUiThread { Toast.makeText(this, "Email оновлено", Toast.LENGTH_SHORT).show() }
                    }
                }
            }

            if (newPassword.isNotBlank()) {
                tasks.add {
                    profileViewModel.changePassword(user.id, "", newPassword) {
                        runOnUiThread { Toast.makeText(this, "Пароль оновлено", Toast.LENGTH_SHORT).show() }
                    }
                }
            }

            // Виконуємо всі задачі
            tasks.forEach { it() }

            // Оновлюємо prefs і UI
            homeViewModel.saveUserToPrefs(updatedUser)
            refreshUI(updatedUser)

            // Вимикаємо редагування
            etName.isEnabled = false
            etEmail.isEnabled = false
            etPassword.isEnabled = false
            btnSave.visibility = Button.GONE
            btnEdit.visibility = Button.VISIBLE

            updateProfilePlaceholders(user)
        }
    }

    protected fun updateProfilePlaceholders(user: UserDto?) {
        user?.let {
            findViewById<EditText>(R.id.etName)?.hint = it.nameUKR
            findViewById<EditText>(R.id.etEmail)?.hint = it.email
            findViewById<EditText>(R.id.etPassword)?.hint = "******"
        }
    }
}