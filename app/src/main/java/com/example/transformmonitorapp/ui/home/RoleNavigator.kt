package com.example.transformmonitorapp.ui.home

import android.content.Context
import android.content.Intent
import com.example.transformmonitorapp.ui.home.role.AdminActivity
import com.example.transformmonitorapp.ui.home.role.AnalystActivity
import com.example.transformmonitorapp.ui.home.role.CreatorActivity
import com.example.transformmonitorapp.ui.home.role.OperatorActivity
import com.example.transformmonitorapp.ui.home.role.UserHomeActivity


class RoleNavigator(private val context: Context) {

    fun navigate(role: String) {
        val intent = when (role.uppercase()) {
            "CREATOR" -> Intent(context, CreatorActivity::class.java)
            "OPERATOR" -> Intent(context, OperatorActivity::class.java)
            "ANALYST" -> Intent(context, AnalystActivity::class.java)
            "ADMIN" -> Intent(context, AdminActivity::class.java)
            else -> Intent(context, UserHomeActivity::class.java)
        }

        intent.apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            context.startActivity(this)
        }
    }
}