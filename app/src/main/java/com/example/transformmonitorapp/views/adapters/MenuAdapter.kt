package com.example.transformmonitorapp.views.adapters

import android.annotation.SuppressLint
import android.graphics.Typeface
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.transformmonitorapp.R

class MenuAdapter(
    private val items: List<MenuItem>
) : RecyclerView.Adapter<MenuAdapter.MenuViewHolder>() {

    inner class MenuViewHolder(val textView: TextView) : RecyclerView.ViewHolder(textView)

    @SuppressLint("ResourceAsColor")
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MenuViewHolder {
        val textView = TextView(parent.context).apply {
            textSize = 18f
            setTypeface(typeface, Typeface.BOLD)
            setPadding(30, 16, 16, 16)
            setTextColor(R.color.textColor)
        }
        return MenuViewHolder(textView)
    }

    override fun onBindViewHolder(holder: MenuViewHolder, position: Int) {
        val item = items[position]
        holder.textView.text = holder.textView.context.getString(item.titleRes) // Встановлюємо текст
        holder.textView.setOnClickListener {
            item.action()
        }
    }

    override fun getItemCount(): Int = items.size
}