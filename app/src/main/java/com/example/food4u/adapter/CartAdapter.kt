package com.example.food4u.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.food4u.R
import com.example.food4u.modal.CartItem
import com.example.food4u.modal.Product
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.ktx.Firebase
import com.squareup.picasso.Picasso

class CartAdapter(private val list: List<Product>, private val agencyId: String, private val cartItem: List<CartItem>, private val listener: CartAdapter.onItemClickListener) : RecyclerView.Adapter<CartAdapter.myViewHolder>() {
    inner class myViewHolder (itemView: View) : RecyclerView.ViewHolder(itemView), View.OnClickListener {

        val tvNecessityName: TextView = itemView.findViewById(R.id.tvProductNecessityName)
        val tvNecessityPrice: TextView = itemView.findViewById(R.id.tvProductNecessityPrice)
        val ivNecessityImg: ImageView = itemView.findViewById(R.id.ivProductNecessityImg)
        val tvNecessityQty: TextView = itemView.findViewById(R.id.tvProductNecessityQuantity)
        val btnNecessityRemove: Button = itemView.findViewById(R.id.btnDeleteProduct)

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
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.view_cycle_product_in_cart, parent, false)

        return myViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: myViewHolder, position: Int) {
        val currentProduct = list[position]
        holder.tvNecessityName.text = currentProduct.name
        holder.tvNecessityPrice.text = "RM" + currentProduct.price.toString()
        if (currentProduct.imageURL.isNotEmpty()){
            Picasso.get().load(currentProduct.imageURL).resize(150, 0).centerCrop().into(holder.ivNecessityImg)
        }
        for (item in cartItem) {
            if (item.productId == currentProduct.id) {
                holder.tvNecessityQty.text = item.qty.toString()
                break
            }
        }

        holder.btnNecessityRemove.setOnClickListener {
            val database = FirebaseDatabase.getInstance().getReference("Cart")
            val userId = FirebaseAuth.getInstance().currentUser!!.uid
            val id = currentProduct.id
            val childUpdates = hashMapOf<String, Any?>(
                "/$userId/$agencyId/$id" to null,
            )
            database.updateChildren(childUpdates)
            this.notifyDataSetChanged()
        }

    }

    override fun getItemCount(): Int {
        return list.size
    }

}