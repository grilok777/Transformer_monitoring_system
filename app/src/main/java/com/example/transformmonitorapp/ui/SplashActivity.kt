package com.example.transformmonitorapp.ui
import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.transformmonitorapp.R
import com.example.transformmonitorapp.network.RetrofitInstance
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


@SuppressLint("CustomSplashScreen")
class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        lifecycleScope.launch {
            val serverAvailable = checkServerConnection()

            if (serverAvailable) {
                startActivity(Intent(this@SplashActivity, RegisterActivity::class.java))
                finish()
            } else {
                Toast.makeText(
                    this@SplashActivity,
                    "Сервер недоступний. Спробуйте пізніше.",
                    Toast.LENGTH_LONG
                ).show()
            }
        }
    }

    private suspend fun checkServerConnection(): Boolean {
        return withContext(Dispatchers.IO) {
            try {
                val response = RetrofitInstance.authApi.ping()
                response.isSuccessful
            } catch (e: Exception) {
                e.printStackTrace()
                false
            }
        }
    }
}