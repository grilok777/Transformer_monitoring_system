package com.example.transformmonitorapp.views.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.transformmonitorapp.R
import com.example.transformmonitorapp.domain.dto.AlertDto

class AlertAdapter(
    private val onResolveClick: (AlertDto) -> Unit
) : RecyclerView.Adapter<AlertAdapter.AlertVH>() {

    private val items = mutableListOf<AlertDto>()

    fun setData(list: List<AlertDto>) {
        items.clear()
        items.addAll(list)
        notifyDataSetChanged()
    }

    class AlertVH(view: View) : RecyclerView.ViewHolder(view) {
        val id: TextView = view.findViewById(R.id.tvAlertId)
        val transformer: TextView = view.findViewById(R.id.tvAlertTransformer)
        val message: TextView = view.findViewById(R.id.tvAlertMessage)
        val level: TextView = view.findViewById(R.id.tvAlertLevel)
        val time: TextView = view.findViewById(R.id.tvAlertTime)
        val status: TextView = view.findViewById(R.id.tvAlertStatus)
        val btnResolve: Button = view.findViewById(R.id.btnResolve)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AlertVH {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_alerts, parent, false)
        return AlertVH(view)
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: AlertVH, position: Int) {
        val a = items[position]

        holder.id.text = "ID: ${a.id}"
        holder.transformer.text = "Transformer: ${a.transformerId}"
        holder.message.text = "Message: ${a.message}"
        holder.level.text = "Level: ${a.level}"
        holder.time.text = "Time: ${a.timestamp}"
        holder.status.text = "Processed: ${a.problemResolved}"

        holder.btnResolve.setOnClickListener { onResolveClick(a) }
    }
}
