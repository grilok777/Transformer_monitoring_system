package com.example.transformmonitorapp.ui

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.transformmonitorapp.R
import com.example.transformmonitorapp.network.RetrofitInstance
import com.example.transformmonitorapp.ui.adapter.UserAdapter
import kotlinx.coroutines.launch
import androidx.core.content.edit
import com.example.transformmonitorapp.dto.UserDto
import com.example.transformmonitorapp.dto.request.LogoutRequest


class HomeActivity : AppCompatActivity() {

    private lateinit var userList: RecyclerView
    private lateinit var adapter: UserAdapter

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        val usernameTextView = findViewById<TextView>(R.id.tvUserName)
        val prefs = getSharedPreferences("prefs", MODE_PRIVATE)
        val name = prefs.getString("USER_NAME", "Користувач")
        usernameTextView.text = "Привіт, $name!"
        val logout = findViewById<TextView>(R.id.btnLogout)
        logout.setOnClickListener {
            lifecycleScope.launch {
                val prefs = getSharedPreferences("prefs", MODE_PRIVATE)
                val token = prefs.getString("JWT_TOKEN", null)

                if (token == null) {
                    Toast.makeText(this@HomeActivity, "Токен відсутній", Toast.LENGTH_SHORT).show()
                    return@launch
                }

                try {
                    val response = RetrofitInstance.authApi.logout(LogoutRequest("Bearer $token"))
                    if (response.isSuccessful) {
                        Toast.makeText(this@HomeActivity, "Вихід успішний", Toast.LENGTH_SHORT).show()
                        prefs.edit { clear() }
                        finish()
                    } else {
                        Toast.makeText(this@HomeActivity, "Помилка: ${response.code()}", Toast.LENGTH_SHORT).show()
                    }
                } catch (e: Exception) {
                    Toast.makeText(this@HomeActivity, "Помилка: ${e.message}", Toast.LENGTH_SHORT).show()
                }
            }
        }

        checkRoleAndRedirect()
    }

    private fun loadUsers() {
        lifecycleScope.launch {
            try {
                val prefs = getSharedPreferences("prefs", MODE_PRIVATE)
                val token = prefs.getString("JWT_TOKEN", null)

                if (token == null) {
                    Toast.makeText(this@HomeActivity, "Токен відсутній, увійди знову", Toast.LENGTH_SHORT).show()
                    return@launch
                }
                try {
                    val users: List<UserDto> = RetrofitInstance.creatorApi.getAllUsers("Bearer $token")

                    val container: LinearLayout = findViewById(R.id.container)
                    container.removeAllViews()

                    for (user in users) {
                        val textView = TextView(this@HomeActivity)
                        textView.text = "${user.nameUKR} (${user.email})"
                        textView.textSize = 16f
                        container.addView(textView)
                    }
                } catch (e: Exception) {
                    Toast.makeText(this@HomeActivity, "Помилка: ${e.message}", Toast.LENGTH_SHORT).show()
                }

            } catch (e: Exception) {
                Toast.makeText(this@HomeActivity, "Помилка: ${e.message}", Toast.LENGTH_SHORT).show()
            }
        }
    }
    private fun checkRoleAndRedirect() {
        lifecycleScope.launch {
            try {
                val prefs = getSharedPreferences("prefs", MODE_PRIVATE)
                val token = prefs.getString("JWT_TOKEN", null) ?: return@launch

                val response = RetrofitInstance.homeApi.getHome("Bearer $token")

                if (response.isSuccessful) {
                    val roleMessage = response.body()?.message ?: "USER_HOME"

                    when (roleMessage) {
                        "ADMIN_HOME" -> showAdminPanel()
                        "CREATOR_HOME" -> loadUsers()
                        "ANALYST_HOME" -> showAnalystPanel()
                        else -> showDefaultPanel()
                    }
                } else {
                    Toast.makeText(this@HomeActivity, "Помилка: ${response.code()}", Toast.LENGTH_SHORT).show()
                }
            } catch (e: Exception) {
                Toast.makeText(this@HomeActivity, "Помилка: ${e.message}", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun showDefaultPanel() {
        TODO("Not yet implemented")
    }

    private fun showAnalystPanel() {
        TODO("Not yet implemented")
    }

    private fun showAdminPanel() {
        TODO("Not yet implemented")
    }

}