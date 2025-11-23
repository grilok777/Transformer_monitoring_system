package com.example.transformmonitorapp.ui.register

import android.content.Intent
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.transformmonitorapp.R
import com.example.transformmonitorapp.data.repository.impl.AuthRepositoryImpl
import com.example.transformmonitorapp.ui.login.LoginActivity
import com.example.transformmonitorapp.views.RegisterViewModelFactory
import com.example.transformmonitorapp.views.models.RegisterViewModel

class RegisterActivity : AppCompatActivity() {

    private val registerViewModel: RegisterViewModel by viewModels {
        RegisterViewModelFactory(application, AuthRepositoryImpl())
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        val baseTitle = findViewById<TextView>(R.id.baseTitle)
        val username = findViewById<EditText>(R.id.usernameDto)
        val email = findViewById<EditText>(R.id.emailDto)
        val password = findViewById<EditText>(R.id.passwordDto)
        val btnRegister = findViewById<Button>(R.id.btnRegister)
        val linkLogin = findViewById<TextView>(R.id.tvGoToLogin)

        baseTitle.text = "Реєстрація"

        btnRegister.setOnClickListener {
            val fullNameText = username.text.toString().trim()
            val emailText = email.text.toString().trim()
            val passwordText = password.text.toString().trim()

            if (fullNameText.isEmpty() || emailText.isEmpty() || passwordText.isEmpty()) {
                Toast.makeText(this, "Заповніть усі поля!", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (!isNetworkAvailable()) {
                Toast.makeText(this, "Немає інтернет-з’єднання!", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            registerViewModel.register(fullNameText, emailText, passwordText)
        }

        registerViewModel.registerResponse.observe(this) { response ->
            if (response?.isSuccessful == true) {
                Toast.makeText(
                    this,
                    response.body()?.message ?: "Успішна реєстрація!",
                    Toast.LENGTH_SHORT
                ).show()
                startActivity(Intent(this, LoginActivity::class.java))
                finish()
            } else if (response != null) {
                Toast.makeText(this, "Помилка: ${response.errorBody()?.string()}", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "Помилка мережі або сервера", Toast.LENGTH_SHORT).show()
            }
        }

        linkLogin.setOnClickListener {
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        }
    }

    private fun isNetworkAvailable(): Boolean {
        val connectivityManager = getSystemService(CONNECTIVITY_SERVICE) as? ConnectivityManager
            ?: return false
        val network = connectivityManager.activeNetwork ?: return false
        val capabilities = connectivityManager.getNetworkCapabilities(network) ?: return false
        return capabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
    }
}