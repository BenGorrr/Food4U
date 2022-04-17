package com.example.food4u.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.food4u.R
import com.example.food4u.modal.Donors
import com.example.food4u.modal.Events
import com.squareup.picasso.Picasso

class DonorsAdapter(private val list: List<Donors>, private val listener: DonorsAdapter.onItemClickListener) : RecyclerView.Adapter<DonorsAdapter.myViewHolder>()  {
    inner class myViewHolder (itemView: View) : RecyclerView.ViewHolder(itemView), View.OnClickListener {

        val tvDonorName: TextView = itemView.findViewById(R.id.tvDonorName)
        val tvDonorDate: TextView = itemView.findViewById(R.id.tvDonorDate)
        val tvDonorPrice: TextView = itemView.findViewById(R.id.tvDonorPrice)
        val imageDonor: ImageView = itemView.findViewById(R.id.imageDonor)

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
        val currentDonors = list[position]
        holder.tvDonorName.text = currentDonors.donorName
        holder.tvDonorDate.text = currentDonors.donateDate
        holder.tvDonorPrice.text = currentDonors.paymentAmount.toString()

        if (currentDonors.imageURL.isNotEmpty()){
            Picasso.get().load(currentDonors.imageURL).resize(150, 0).centerCrop().into(holder.imageDonor)
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }
}