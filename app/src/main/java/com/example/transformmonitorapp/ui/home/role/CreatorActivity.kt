package com.example.transformmonitorapp.ui.home.role

import GenericViewModelFactory
import android.os.Bundle
import android.widget.*
import androidx.activity.viewModels
import androidx.core.view.GravityCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.transformmonitorapp.R
import com.example.transformmonitorapp.data.repository.impl.CreatorRepositoryImpl
import com.example.transformmonitorapp.views.adapters.MenuAdapter
import com.example.transformmonitorapp.views.adapters.MenuItem
import com.example.transformmonitorapp.views.adapters.UserAdapter
import com.example.transformmonitorapp.views.models.CreatorViewModel

class CreatorActivity : RoleActivity() {

    private val token: String by lazy { homeViewModel.loadToken() ?: "" }

    private val viewModel: CreatorViewModel by viewModels {
        GenericViewModelFactory {
            CreatorViewModel(application, CreatorRepositoryImpl(applicationContext, token))
        }
    }

    private lateinit var userAdapter: UserAdapter
    private lateinit var rvUsers: RecyclerView

    override fun getLayoutId(): Int = R.layout.layout_profile

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        observeViewModel()
        setupProfileView()
    }

    private fun observeViewModel() {
        viewModel.users.observe(this) { users ->
            if (::userAdapter.isInitialized) userAdapter.updateData(users)
        }

        viewModel.error.observe(this) { msg ->
            Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
        }
    }

    override fun customizeAsideMenu() {
        val menuItems = listOf(
            MenuItem(
                titleRes = R.string.all_users,
                action = {
                    handleMenuAction(R.string.all_users, R.layout.activity_users) {
                        setupRecyclerView()
                        viewModel.loadUsers()
                    }
                }
            ),
            MenuItem(
                titleRes = R.string.search,
                action = {
                    handleMenuAction(R.string.search, R.layout.activity_search_user) {
                        setupSearchView()
                    }
                }
            )
        )

        menuContainer.addView(
            RecyclerView(this).apply {
                layoutManager = LinearLayoutManager(this@CreatorActivity)
                adapter = MenuAdapter(menuItems)
            }
        )
    }

    private fun handleMenuAction(titleRes: Int, layoutRes: Int, extraAction: (() -> Unit)? = null) {
        header.tvTitle.setText(titleRes)
        setContentLayout(layoutRes)
        extraAction?.invoke()
        drawerLayout.closeDrawer(GravityCompat.START)
    }

    private fun setupRecyclerView() {
        rvUsers = findViewById(R.id.rvUsers)
        rvUsers.layoutManager = LinearLayoutManager(this)
        userAdapter = UserAdapter { user, newRole ->
            viewModel.changeRole(user.id, newRole)
        }
        rvUsers.adapter = userAdapter
    }

    private fun setupSearchView() {
        val searchField = findViewById<EditText>(R.id.etSearchEmail)
        val btnSearch = findViewById<Button>(R.id.btnSearch)

        btnSearch.setOnClickListener {
            val email = searchField.text.toString()
            if (email.isNotBlank()) {
                viewModel.loadUserByEmail(email)
            } else {
                Toast.makeText(this, "Введіть email для пошуку", Toast.LENGTH_SHORT).show()
            }
        }
    }
}