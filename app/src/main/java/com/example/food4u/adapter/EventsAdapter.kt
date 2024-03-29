package com.example.food4u.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.food4u.R
import com.example.food4u.modal.Events
import com.example.food4u.modal.Product
import com.squareup.picasso.Picasso

class EventsAdapter(private val list: List<Events>, private val listener: EventsAdapter.onItemClickListener) : RecyclerView.Adapter<EventsAdapter.myViewHolder>() {
    inner class myViewHolder (itemView: View) : RecyclerView.ViewHolder(itemView), View.OnClickListener {

        val tvFEventTittle:TextView = itemView.findViewById(R.id.tvFEventTittle)
        val tvFEventContent:TextView = itemView.findViewById(R.id.tvFEventContent)
        val imageFEvent:ImageView = itemView.findViewById(R.id.imageFEvent)

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
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.view_cycle_fundraising_event, parent, false)

        return myViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: myViewHolder, position: Int) {
        val currentEvents = list[position]
        holder.tvFEventTittle.text = currentEvents.eventTitle
        holder.tvFEventContent.text = currentEvents.description

        if (currentEvents.imageURL.isNotEmpty()){
            Picasso.get().load(currentEvents.imageURL).resize(150, 0).centerCrop().into(holder.imageFEvent)
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }
}