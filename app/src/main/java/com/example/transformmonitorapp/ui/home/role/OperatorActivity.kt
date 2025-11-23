package com.example.transformmonitorapp.ui.home.role

import android.os.Bundle
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.transformmonitorapp.R

class OperatorActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_operator)

        val title = findViewById<TextView>(R.id.baseTitle)
        val userName = findViewById<TextView>(R.id.tvUserName)
        val container = findViewById<LinearLayout>(R.id.roleContentContainer)
        val logout = findViewById<Button>(R.id.btnLogout)

        title.text = "Operator Panel"
        userName.text = "Welcome, Operator"

        // Example content
        val text = TextView(this)
        text.text = "Admin tools will be here"
        text.textSize = 18f
        container.addView(text)
    }
}
