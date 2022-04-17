package com.example.food4u.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.food4u.R
import com.example.food4u.modal.Events
import com.example.food4u.modal.Notification
import com.squareup.picasso.Picasso

class NotificationAdapter (private val list: List<Notification>, private val listener: NotificationAdapter.onItemClickListener) : RecyclerView.Adapter<NotificationAdapter.myViewHolder>(){
    inner class myViewHolder (itemView: View) : RecyclerView.ViewHolder(itemView), View.OnClickListener {

        val noficationTitle: TextView = itemView.findViewById(R.id.notificationTitles)
        val notificationMessage: TextView = itemView.findViewById(R.id.notificationMessages)
        val notificationTime: TextView = itemView.findViewById(R.id.notificationTime)

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
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.view_cycle_notification, parent, false)

        return myViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: myViewHolder, position: Int) {
        val currentNoti = list[position]
        holder.noficationTitle.text = currentNoti.title
        holder.notificationMessage.text = currentNoti.msg
        holder.notificationTime.text = currentNoti.date
    }

    override fun getItemCount(): Int {
        return list.size
    }
}