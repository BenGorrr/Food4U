package com.example.food4u.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.food4u.R
import com.example.food4u.modal.TransactionHistory
import java.time.Month

class TransactionHistoryAdapter(private val list: List<TransactionHistory>, private val listener: TransactionHistoryAdapter.onItemClickListener) : RecyclerView.Adapter<TransactionHistoryAdapter.myViewHolder>() {
    inner class myViewHolder (itemView: View) : RecyclerView.ViewHolder(itemView), View.OnClickListener {

        val donationTitle: TextView = itemView.findViewById(R.id.donationTitle)
        val donationCampaign: TextView = itemView.findViewById(R.id.donationCampaign)
        val donationAmount: TextView = itemView.findViewById(R.id.donationAmount)
        val donationDay: TextView = itemView.findViewById(R.id.dayTxt)
        val donationMonth: TextView = itemView.findViewById(R.id.monthTxt)

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
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.view_cycle_donations, parent, false)

        return myViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: myViewHolder, position: Int) {
        val currentTrans = list[position]
        holder.donationCampaign.text = currentTrans.name
        holder.donationAmount.text = "RM" + currentTrans.amount.toString()
        val date = currentTrans.date
        val dateArr = date.split("/")
        holder.donationDay.text = dateArr[0]
        val month = Month.of(dateArr[1].toString().toInt())
        holder.donationMonth.text = month.toString()
        holder.donationTitle.visibility = View.INVISIBLE
    }

    override fun getItemCount(): Int {
        return list.size
    }
}