package com.example.transformmonitorapp.ui.splash

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.transformmonitorapp.R
import com.example.transformmonitorapp.data.repository.impl.AuthRepositoryImpl
import com.example.transformmonitorapp.data.repository.interfaces.AuthRepository
import com.example.transformmonitorapp.ui.register.RegisterActivity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.concurrent.TimeUnit

@SuppressLint("CustomSplashScreen")
class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        lifecycleScope.launch {
            val available = waitUntilServerOnline(AuthRepositoryImpl(applicationContext))

            if (available) {
                startActivity(Intent(this@SplashActivity, RegisterActivity::class.java))
                finish()
            } else {
                Toast.makeText(
                    this@SplashActivity,
                    R.string.serverNotAvailable,
                    Toast.LENGTH_LONG
                ).show()
            }
        }
    }

    private suspend fun checkServerConnection(authRepository: AuthRepository): Boolean {
        return withContext(Dispatchers.IO) {
            try {
                val response = authRepository.pingUser()
                response.isSuccessful
            } catch (e: Exception) {
                e.printStackTrace()
                false
            }
        }
    }
    private suspend fun waitUntilServerOnline(
        authRepository: AuthRepository,
        maxAttempts: Int = 5
    ): Boolean {

        repeat(maxAttempts) { attempt ->
            val success = checkServerConnection(authRepository)

            if (success) return true

            delay(TimeUnit.SECONDS.toMillis(3))
        }

        return false
    }
}