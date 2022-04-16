package com.example.food4u.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.food4u.R
import com.example.food4u.modal.Product
import com.squareup.picasso.Picasso

class ProductsAdapter (private val list: List<Product>, private val listener: ProductsAdapter.onItemClickListener) : RecyclerView.Adapter<ProductsAdapter.myViewHolder>(){

    inner class myViewHolder (itemView: View) : RecyclerView.ViewHolder(itemView), View.OnClickListener {

        val tvNecessityName: TextView = itemView.findViewById(R.id.tvNecessityName)
        val tvNecessityPrice: TextView = itemView.findViewById(R.id.tvNecessityPrice)
        val ivNecessityImg: ImageView = itemView.findViewById(R.id.imageNecessity)

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
        //fun plusBtnClick(position: Int)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): myViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.rv_necessity_product, parent, false)

        return myViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: myViewHolder, position: Int) {
        val currentAgency = list[position]
        holder.tvNecessityName.text = currentAgency.name
        holder.tvNecessityPrice.text = currentAgency.price.toString()
        if (currentAgency.imageURL.isNotEmpty()){
            Picasso.get().load(currentAgency.imageURL).resize(150, 0).centerCrop().into(holder.ivNecessityImg)
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }
}