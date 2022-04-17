package com.example.food4u.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.food4u.R
import com.example.food4u.modal.Donors
import com.example.food4u.modal.EventPayment
import com.example.food4u.modal.Events
import com.example.food4u.modal.User
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.squareup.picasso.Picasso
import okhttp3.internal.notifyAll

class DonorsAdapter(private val list: List<EventPayment>, private val listener: DonorsAdapter.onItemClickListener) : RecyclerView.Adapter<DonorsAdapter.myViewHolder>()  {

    private lateinit var database : DatabaseReference

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
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.view_cycle_donor, parent, false)


        return myViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: myViewHolder, position: Int) {
        val currentDonors = list[position]

        database = FirebaseDatabase.getInstance().getReference("userDB/User")
        database.child(currentDonors.userId).get().addOnSuccessListener {
            val user = it.getValue(User::class.java)!!

            holder.tvDonorName.text = user.name
            if (user.imgUrl.toString().isNotEmpty()){
                Picasso.get().load(user.imgUrl.toString()).resize(150, 0).centerCrop().into(holder.imageDonor)
            } else {
                holder.imageDonor.setImageResource(R.drawable.userlogo)
            }
        }.addOnFailureListener{
            Log.d("Load to View", "Failed  " + it.message)
        }
        holder.tvDonorPrice.text = "+ ${currentDonors.amount.toString()}"
        holder.tvDonorDate.text = currentDonors.date


    }

    override fun getItemCount(): Int {
        return list.size
    }
}