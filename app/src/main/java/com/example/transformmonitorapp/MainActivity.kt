//package com.example.transformmonitorapp
//import android.content.Intent
//import android.os.Bundle
//import android.os.Handler
//import android.os.Looper
//import androidx.appcompat.app.AppCompatActivity
//import com.example.transformmonitorapp.ui.HomeActivity
//import com.example.transformmonitorapp.ui.RegisterActivity
//import com.example.transformmonitorapp.ui.SplashActivity
//
//class MainActivity : AppCompatActivity() {
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//
//        // Відразу вирішуємо, що показати
//        // Наприклад, сплеш завжди
//        startSplash()
//    }
//
//    private fun startSplash() {
//        // Запускаємо SplashActivity на 2 секунди
//        val splashIntent = Intent(this, SplashActivity::class.java)
//        startActivity(splashIntent)
//
//        // Через 2 секунди переходимо далі
//        Handler(Looper.getMainLooper()).postDelayed({
//            // Тут можна перевірити, зареєстрований користувач чи ні
//            val isRegistered = false // TODO: замінити на реальну перевірку
//            if (isRegistered) {
//                startActivity(Intent(this, HomeActivity::class.java))
//            } else {
//                startActivity(Intent(this, RegisterActivity::class.java))
//            }
//            finish() // закриваємо MainActivity
//        }, 2000)
//    }
//}
