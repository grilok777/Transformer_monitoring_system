package com.example.transformmonitorapp.views.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.Spinner
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.transformmonitorapp.R
import com.example.transformmonitorapp.domain.dto.UserDto
import com.example.transformmonitorapp.domain.model.Role
class UserAdapter(
    private val onChangeRoleClick: (UserDto, String) -> Unit
) : RecyclerView.Adapter<UserAdapter.UserViewHolder>() {

    private val roles = Role.entries.map { it.name }
    private var users: List<UserDto> = emptyList()

    private lateinit var roleAdapter: ArrayAdapter<String>

    init {
        setHasStableIds(true)
    }

    class UserViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val rvUserName: TextView = view.findViewById(R.id.rvUserName)
        val rvUserEmail: TextView = view.findViewById(R.id.rvUserEmail)
        val btnChangeRole: Button = view.findViewById(R.id.btnChangeRole)
        val spinnerRole: Spinner = view.findViewById(R.id.spinnerRole)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        if (!::roleAdapter.isInitialized) {
            roleAdapter = ArrayAdapter(
                parent.context,
                android.R.layout.simple_spinner_item,
                roles
            ).apply {
                setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            }
        }

        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_user, parent, false)

        val holder = UserViewHolder(view)

        holder.spinnerRole.adapter = roleAdapter

        return holder
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        val user = users[position]

        holder.rvUserName.text = user.nameUKR
        holder.rvUserEmail.text = user.email

        // Встановлюємо вибране значення БЕЗ тригеру listener
        val roleIndex = roles.indexOf(user.role.name)
        if (holder.spinnerRole.selectedItemPosition != roleIndex) {
            holder.spinnerRole.setSelection(roleIndex, false)
        }

        holder.btnChangeRole.setOnClickListener {
            val newRole = holder.spinnerRole.selectedItem.toString()
            onChangeRoleClick(user, newRole)
        }
    }

    override fun getItemCount() = users.size

    override fun getItemId(position: Int) = users[position].id

    @SuppressLint("NotifyDataSetChanged")
    fun updateData(newUsers: List<UserDto>) {
        users = newUsers
        notifyDataSetChanged()
    }
}