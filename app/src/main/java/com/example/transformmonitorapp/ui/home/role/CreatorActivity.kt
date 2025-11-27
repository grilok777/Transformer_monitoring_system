//package com.example.transformmonitorapp.ui.home.role
//
//import com.example.transformmonitorapp.views.models.CreatorViewModel
//import GenericViewModelFactory
//import android.os.Bundle
//import android.widget.Button
//import android.widget.EditText
//import android.widget.Toast
//import androidx.activity.viewModels
//import androidx.core.view.GravityCompat
//import androidx.recyclerview.widget.LinearLayoutManager
//import androidx.recyclerview.widget.RecyclerView
//import com.example.transformmonitorapp.R
//import com.example.transformmonitorapp.data.repository.impl.CreatorRepositoryImpl
//import com.example.transformmonitorapp.views.adapters.MenuAdapter
//import com.example.transformmonitorapp.views.adapters.MenuItem
//import com.example.transformmonitorapp.views.adapters.UserAdapter
//
//class CreatorActivity : RoleActivity() {
//
//    val token = pref
//        private val viewModel: CreatorViewModel by viewModels {
//        GenericViewModelFactory {
//            CreatorViewModel(application, CreatorRepositoryImpl(applicationContext, token))
//        }
//    }
//
//
//    private lateinit var userAdapter: UserAdapter
//    private lateinit var rvUsers: RecyclerView
//
//    override fun getLayoutId(): Int = R.layout.activity_creator
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        observeViewModel()
//        setupRecyclerView()
//    }
//
//    private fun observeViewModel() {
//        viewModel.users.observe(this) { users ->
//            if (::userAdapter.isInitialized) userAdapter.updateData(users)
//        }
//        viewModel.error.observe(this) { msg ->
//            Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
//        }
//    }
//
//    override fun customizeAsideMenu() {
//        fun menuAction(titleRes: Int, layoutRes: Int, action: (() -> Unit)? = null) = {
//            header.tvTitle.setText(titleRes)
//            setContentLayout(layoutRes)
//            if (::rvUsers.isInitialized) setupRecyclerView()
//            action?.invoke()
//            drawerLayout.closeDrawer(GravityCompat.START)
//        }
//
//        val menuItems = listOf(
//            MenuItem(R.string.all_users, menuAction(R.string.all_users, R.layout.activity_creator) {
//                viewModel.loadUsers()
//            }),
//            MenuItem(R.string.search, menuAction(R.string.search, R.layout.activity_search_user) {
//                setupSearchView()
//            })
//        )
//
//        menuContainer.addView(
//            RecyclerView(this).apply {
//                layoutManager = LinearLayoutManager(this@CreatorActivity)
//                adapter = MenuAdapter(menuItems)
//            }
//        )
//    }
//
//    private fun setupRecyclerView() {
//        rvUsers = findViewById(R.id.rvUsers)
//        rvUsers.layoutManager = LinearLayoutManager(this)
//        userAdapter = UserAdapter { user, newRole -> viewModel.changeRole(user.id, newRole) }
//        rvUsers.adapter = userAdapter
//    }
//
//    private fun setupSearchView() {
//        val searchField = findViewById<EditText>(R.id.etSearchEmail)
//        val btnSearch = findViewById<Button>(R.id.btnSearch)
//
//        btnSearch.setOnClickListener {
//            val email = searchField.text.toString()
//            viewModel.loadUserByEmail(email)
//        }
//    }
//}
package com.example.transformmonitorapp.ui.home.role

import GenericViewModelFactory
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
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

    val token: String by lazy { homeViewModel.loadToken() ?: "" }

    private val viewModel: CreatorViewModel by viewModels {
        GenericViewModelFactory { CreatorViewModel(application, CreatorRepositoryImpl(applicationContext, token)) }
    }

    private lateinit var userAdapter: UserAdapter
    private lateinit var rvUsers: RecyclerView

    override fun getLayoutId(): Int = R.layout.activity_creator

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        observeViewModel()
        setupRecyclerView()
    }

    private fun observeViewModel() {
        viewModel.users.observe(this) { users -> if (::userAdapter.isInitialized) userAdapter.updateData(users) }
        viewModel.error.observe(this) { msg -> Toast.makeText(this, msg, Toast.LENGTH_SHORT).show() }
    }

    override fun customizeAsideMenu() {
        val menuItems = listOf(
            MenuItem(
                titleRes = R.string.all_users,
                action = { handleMenuAction(R.string.all_users, R.layout.activity_creator) { viewModel.loadUsers() } }
            ),
            MenuItem(
                titleRes = R.string.search,
                action = { handleMenuAction(R.string.search, R.layout.activity_search_user) { setupSearchView() } }
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
        if (::rvUsers.isInitialized) setupRecyclerView()
        extraAction?.invoke()
        drawerLayout.closeDrawer(GravityCompat.START)
    }

    override fun navigateToProfile() {
        setContentLayout(R.layout.activity_creator)

        header.tvTitle.setText(R.string.profile)
    }

    private fun setupRecyclerView() {
        rvUsers = findViewById(R.id.rvUsers)
        rvUsers.layoutManager = LinearLayoutManager(this)
        userAdapter = UserAdapter { user, newRole -> viewModel.changeRole(user.id, newRole) }
        rvUsers.adapter = userAdapter
    }

    private fun setupSearchView() {
        val searchField = findViewById<EditText>(R.id.etSearchEmail)
        val btnSearch = findViewById<Button>(R.id.btnSearch)
        btnSearch.setOnClickListener { viewModel.loadUserByEmail(searchField.text.toString()) }
    }
}
