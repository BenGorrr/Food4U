package com.example.food4u.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
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
        val ivNecessityQty: TextView = itemView.findViewById(R.id.tvNecessityQuantity)
        val btnIncQty: Button = itemView.findViewById(R.id.btnAddNecessity)
        val btnDecQty: Button = itemView.findViewById(R.id.btnDeductNecessity)
        val btnAddToCart: Button = itemView.findViewById(R.id.btnAddToCartNecessity)

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
        fun onAddToCart(product: Product, qty: Int)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): myViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.rv_necessity_product, parent, false)

        return myViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: myViewHolder, position: Int) {
        val currentProduct = list[position]
        holder.tvNecessityName.text = currentProduct.name
        holder.tvNecessityPrice.text = "RM" + currentProduct.price.toString()
        if (currentProduct.imageURL.isNotEmpty()){
            Picasso.get().load(currentProduct.imageURL).resize(150, 0).centerCrop().into(holder.ivNecessityImg)
        }

        holder.btnIncQty.setOnClickListener {
           // listener.onPlusBtnClick(currentProduct)
            var qty = holder.ivNecessityQty.text.toString().toInt()
            if (qty < 10) {
                qty++
                holder.ivNecessityQty.text = qty.toString()
            }

        }
        holder.btnDecQty.setOnClickListener {
            var qty = holder.ivNecessityQty.text.toString().toInt()
            if (qty > 0) {
                qty--
                holder.ivNecessityQty.text = qty.toString()
            }

        }
        holder.btnAddToCart.setOnClickListener {
            var qty = holder.ivNecessityQty.text.toString().toInt()
            if (qty > 0)
                listener.onAddToCart(currentProduct, qty)
        }



    }

    override fun getItemCount(): Int {
        return list.size
    }


}