package com.example.food4u.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.food4u.R
import com.example.food4u.modal.Agency

class AgencyAdapter(private val list: List<Agency>, private val listener: onItemClickListener) : RecyclerView.Adapter<AgencyAdapter.myViewHolder>() {

    inner class myViewHolder (itemView: View) : RecyclerView.ViewHolder(itemView), View.OnClickListener {

        val tvAgencyName: TextView = itemView.findViewById(R.id.tvAgencyName)
        val tvAgencyDesc: TextView = itemView.findViewById(R.id.tvAgencyDesc)

        init {
            itemView.setOnClickListener(this)
        }

        override fun onClick(view: View?) {
            val position = bindingAdapterPosition

            if (position != RecyclerView.NO_POSITION) {
                listener.itemClick(position)
            }
        }

    }

    interface onItemClickListener {
        fun itemClick(position: Int)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): myViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.rv_agent, parent, false)

        return myViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: myViewHolder, position: Int) {
        val currentAgency = list[position]
        holder.tvAgencyName.text = currentAgency.name
        holder.tvAgencyDesc.text = currentAgency.description

    }

    override fun getItemCount(): Int {
        return list.size
    }
}