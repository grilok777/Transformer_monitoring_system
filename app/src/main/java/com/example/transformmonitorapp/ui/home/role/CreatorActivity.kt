//package com.example.transformmonitorapp.ui.home.role
//
//import android.annotation.SuppressLint
//import android.os.Bundle
//import android.widget.LinearLayout
//import android.widget.TextView
//import android.widget.Toast
//import androidx.activity.viewModels
//import androidx.appcompat.app.AppCompatActivity
//import com.example.transformmonitorapp.R
//import com.example.transformmonitorapp.data.network.ApiServiceProvider
//import com.example.transformmonitorapp.data.repository.impl.CreatorRepositoryImpl
//import com.example.transformmonitorapp.domain.dto.UserDto
//import com.example.transformmonitorapp.views.factories.CreatorViewModelFactory
//import com.example.transformmonitorapp.views.models.CreatorViewModel
//
//class CreatorActivity : AppCompatActivity() {
//
//    private val viewModel: CreatorViewModel by viewModels {
//        CreatorViewModelFactory(
//            application,
//            CreatorRepositoryImpl(ApiServiceProvider.creatorApi)
//        )
//    }
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_creator)
//
//        val tvUserName = findViewById<TextView>(R.id.tvUserName)
//        tvUserName.text = "Привіт, Creator!"
//
//        val roleContainer = findViewById<LinearLayout>(R.id.roleContentContainer)
//        viewModel.users.observe(this) { users ->
//            roleContainer.removeAllViews()
//            users.forEach { user ->
//                val tv = TextView(this)
//                tv.text = "${user.nameUKR} (${user.email})"
//                tv.textSize = 16f
//                roleContainer.addView(tv)
//            }
//        }
//    }
//
//
//    private fun setupUI() {
//        val title = findViewById<TextView>(R.id.baseTitle)
//        title.text = "Creator Panel"
//
//        val userName = findViewById<TextView>(R.id.tvUserName)
//        userName.text = "Loading users..."
//    }
//
//    private fun observeViewModel() {
//        viewModel.users.observe(this) { showUsers(it) }
//        viewModel.error.observe(this) {
//            Toast.makeText(this, it, Toast.LENGTH_SHORT).show()
//        }
//    }
//
//    @SuppressLint("SetTextI18n")
//    private fun showUsers(users: List<UserDto>) {
//        val container = findViewById<LinearLayout>(R.id.roleContentContainer)
//        container.removeAllViews()
//
//        users.forEach { user ->
//            val tv = TextView(this).apply {
//                text = "${user.nameUKR} (${user.email})"
//                textSize = 16f
//            }
//            container.addView(tv)
//        }
//    }
//}
package com.example.transformmonitorapp.ui.home.role

import GenericViewModelFactory
import android.os.Bundle
import android.os.PersistableBundle
import androidx.activity.viewModels
import com.example.transformmonitorapp.data.network.ApiServiceProvider
import com.example.transformmonitorapp.data.repository.impl.CreatorRepositoryImpl
import com.example.transformmonitorapp.views.models.CreatorViewModel

class CreatorActivity : RoleActivity() {

    private val viewModel: CreatorViewModel by viewModels {
        GenericViewModelFactory {
            val repository = CreatorRepositoryImpl(ApiServiceProvider.creatorApi)

            CreatorViewModel(application, repository)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        super.onCreate(savedInstanceState, persistentState)
        val users = viewModel.loadUsers()
    }

    override fun setupUI() {
        super.setupUI()
        tvUserName.text = "Привіт, Creator!"
    }

    override fun observeViewModel() {
        viewModel.error.observe(this) { showError(it) }
    }

    override fun loadData() {
        viewModel.loadUsers()
    }
    fun showUsers(){
    }
}