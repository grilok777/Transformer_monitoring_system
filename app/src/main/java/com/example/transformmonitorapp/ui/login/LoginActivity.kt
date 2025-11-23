package com.example.transformmonitorapp.ui.login

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.transformmonitorapp.R
import com.example.transformmonitorapp.data.repository.impl.AuthRepositoryImpl
import com.example.transformmonitorapp.ui.home.RoleNavigator
import com.example.transformmonitorapp.ui.register.RegisterActivity
import com.example.transformmonitorapp.views.models.LoginViewModel
import com.example.transformmonitorapp.views.factories.LoginViewModelFactory

class LoginActivity : AppCompatActivity() {

    private val loginViewModel: LoginViewModel by viewModels {
        LoginViewModelFactory(application, AuthRepositoryImpl())
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val baseTitle = findViewById<TextView>(R.id.baseTitle)
        val emailField = findViewById<EditText>(R.id.emailDto)
        val passwordField = findViewById<EditText>(R.id.passwordDto)
        val btnLogin = findViewById<Button>(R.id.btnLogin)
        val linkRegister = findViewById<TextView>(R.id.tvGoToRegister)

        baseTitle.text = getString(R.string.login)

        linkRegister.setOnClickListener {
            startActivity(Intent(this, RegisterActivity::class.java))
            finish()
        }

        btnLogin.setOnClickListener {
            val email = emailField.text.toString()
            val password = passwordField.text.toString()

            if (email.isBlank() || password.isBlank()) {
                Toast.makeText(this, "Введіть email та пароль", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            loginViewModel.login(email, password)
        }

        loginViewModel.loginResponse.observe(this) { response ->
            if (response?.isSuccessful == true) {
                val body = response.body()
                if (body != null) {
                    loginViewModel.saveUserData(body.token, body.userDto)
                    Toast.makeText(this, "Успішний вхід!", Toast.LENGTH_SHORT).show()

                    val roleNavigator = RoleNavigator(this)
                    roleNavigator.navigate(body.userDto.role.name)

                    finish()
                }
            } else {
                Toast.makeText(this, "Невірний email або пароль", Toast.LENGTH_SHORT).show()
            }
        }

    }
}