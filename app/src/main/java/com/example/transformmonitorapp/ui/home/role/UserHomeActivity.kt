package com.example.transformmonitorapp.ui.home.role

import android.annotation.SuppressLint
import android.graphics.Typeface
import android.os.Bundle
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.transformmonitorapp.R

class UserHomeActivity : AppCompatActivity() {
    lateinit var waitMessage: TextView

    @SuppressLint("ResourceAsColor")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.layout_base_role)

        val title = findViewById<TextView>(R.id.tvUserName)
        title.apply {
            text = "Отакої :("
            textSize = 24f
            setTextColor(R.color.textColor)
            setTypeface(typeface, Typeface.BOLD)
        }

        waitMessage = TextView(this).apply {
            text = "Будь ласка, зачекайте перевірку від модерації серверу"
            textSize = 18f
            setPadding(16, 16, 16, 16)

        }

        val container = findViewById<LinearLayout>(R.id.roleContentContainer)
        container.addView(waitMessage)
    }
}