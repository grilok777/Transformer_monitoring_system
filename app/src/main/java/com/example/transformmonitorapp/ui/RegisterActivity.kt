package com.example.transformmonitorapp.ui

import android.Manifest
import android.content.Intent
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.annotation.RequiresPermission
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.transformmonitorapp.R
import com.example.transformmonitorapp.dto.request.RegisterRequest
import com.example.transformmonitorapp.network.RetrofitInstance
import kotlinx.coroutines.launch
import java.net.SocketTimeoutException
import java.net.UnknownHostException

class RegisterActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        val username = findViewById<EditText>(R.id.usernameDto)
        val email = findViewById<EditText>(R.id.emailDto)
        val password = findViewById<EditText>(R.id.passwordDto)
        val btnRegister = findViewById<Button>(R.id.btnRegister)
        val btnLogin = findViewById<Button>(R.id.btnLogin)

        btnRegister.setOnClickListener {
            val fullNameText = username.text.toString().trim()
            val emailText = email.text.toString().trim()
            val passwordText = password.text.toString().trim()

            if (fullNameText.isEmpty() || emailText.isEmpty() || passwordText.isEmpty()) {
                Toast.makeText(this, "Заповніть усі поля!", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (!isNetworkAvailable()) {
                Toast.makeText(this, "Немає з'єднання з інтернетом!", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            lifecycleScope.launch {
                try {
                    val response = RetrofitInstance.authApi.register(
                        RegisterRequest(emailText, passwordText, fullNameText)
                    )

                    if (response.isSuccessful) {
                        val msg = response.body()?.message ?: "Успішна реєстрація!"
                        Toast.makeText(this@RegisterActivity, msg, Toast.LENGTH_SHORT).show()
                        startActivity(Intent(this@RegisterActivity, LoginActivity::class.java))
                        finish()
                    } else {
                        val errorMsg = response.errorBody()?.string() ?: "Помилка реєстрації"
                        Toast.makeText(this@RegisterActivity, errorMsg, Toast.LENGTH_SHORT).show()
                    }
                } catch (_: UnknownHostException) {
                    Toast.makeText(this@RegisterActivity, "Сервер недоступний. Перепідключення...", Toast.LENGTH_SHORT).show()
                    restartActivity()
                } catch (_: SocketTimeoutException) {
                    Toast.makeText(this@RegisterActivity, "Перевищено час очікування відповіді сервера", Toast.LENGTH_SHORT).show()
                    restartActivity()
                } catch (e: Exception) {
                    Toast.makeText(this@RegisterActivity, "Помилка: ${e.message}", Toast.LENGTH_SHORT).show()
                }
            }
        }

        btnLogin.setOnClickListener {
            startActivity(Intent(this@RegisterActivity, LoginActivity::class.java))
            finish()
        }
    }

    @RequiresPermission(Manifest.permission.ACCESS_NETWORK_STATE)
    private fun isNetworkAvailable(): Boolean {
        val connectivityManager = getSystemService(CONNECTIVITY_SERVICE) as ConnectivityManager
        val network = connectivityManager.activeNetwork ?: return false
        val capabilities = connectivityManager.getNetworkCapabilities(network) ?: return false
        return capabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
    }

    private fun restartActivity() {
        val intent = Intent(this, RegisterActivity::class.java)
        finish()
        startActivity(intent)
    }
}
