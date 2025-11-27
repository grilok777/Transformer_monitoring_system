package com.example.transformmonitorapp.ui.home.role

import android.annotation.SuppressLint
import android.graphics.Typeface
import android.os.Bundle
import android.widget.TextView
import com.example.transformmonitorapp.R

class UserHomeActivity : RoleActivity() {
    lateinit var waitMessage: TextView

    override fun getLayoutId(): Int = R.layout.activity_undefined

    @SuppressLint("ResourceAsColor")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val title = findViewById<TextView>(R.id.tvTitle)
        title.apply {
            text = "Отакої :("
            textSize = 24f
            setTextColor(R.color.textColor)
            setTypeface(typeface, Typeface.BOLD)
        }

        waitMessage = findViewById(R.id.errorMsg)

        waitMessage.apply{
            text = "Будь ласка, зачекайте перевірку від модерації серверу"
            textSize = 18f
            setPadding(16, 16, 16, 16)
            textAlignment = TextView.TEXT_ALIGNMENT_CENTER
        }
    }

    override fun customizeAsideMenu() {
    }

    override fun navigateToProfile() {
    }
}