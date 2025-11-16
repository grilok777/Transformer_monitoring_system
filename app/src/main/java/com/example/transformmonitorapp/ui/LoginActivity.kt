package com.example.transformmonitorapp.ui

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.transformmonitorapp.R
import com.example.transformmonitorapp.dto.request.LoginRequest
import com.example.transformmonitorapp.network.RetrofitInstance
import kotlinx.coroutines.launch

class LoginActivity : AppCompatActivity() {

    @SuppressLint("UseKtx")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val email = findViewById<EditText>(R.id.emailDto)
        val password = findViewById<EditText>(R.id.passwordDto)
        val btnLogin = findViewById<Button>(R.id.btnLogin)
        val btnRegister = findViewById<Button>(R.id.btnRegister)

        btnRegister.setOnClickListener {
            startActivity(Intent(this, RegisterActivity::class.java))
        }
        btnLogin.setOnClickListener {
            val emailText = email.text.toString()
            val passwordText = password.text.toString()

            lifecycleScope.launch {
                try {
                    val response = RetrofitInstance.authApi.login(LoginRequest(emailText, passwordText))
                    if (response.isSuccessful) {
                        val jwt = response.body()?.token
                        val userDto = response.body()?.userDto

                        if (jwt != null && userDto != null) {

                            val nameUKR = userDto.nameUKR
                            val email = userDto.email
                            val role = userDto.role

                            val prefs = getSharedPreferences("prefs", MODE_PRIVATE)
                            prefs.edit()
                                .putString("JWT_TOKEN", jwt)
                                .putString("USER_NAME", nameUKR)
                                .putString("USER_EMAIL", email)
                                .putString("USER_ROLE", role.name)
                                .apply()

                            Toast.makeText(this@LoginActivity, "Успішний вхід!", Toast.LENGTH_SHORT).show()
                            startActivity(Intent(this@LoginActivity, HomeActivity::class.java))
                            finish()
                        }
                    } else {
                        Toast.makeText(this@LoginActivity, "Неправильний email або пароль", Toast.LENGTH_SHORT).show()
                    }
                } catch (e: Exception) {
                    Toast.makeText(this@LoginActivity, "Помилка: ${e.message}", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}
