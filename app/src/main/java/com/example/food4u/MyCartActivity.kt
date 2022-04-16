package com.example.food4u

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.food4u.adapter.CartAdapter
import com.example.food4u.adapter.ProductsAdapter
import com.example.food4u.databinding.ActivityMyCartBinding
import com.example.food4u.modal.CartItem
import com.example.food4u.modal.Product
import com.google.firebase.database.*

class MyCartActivity : AppCompatActivity(), CartAdapter.onItemClickListener {
    private lateinit var binding: ActivityMyCartBinding
    private lateinit var database: DatabaseReference
    private lateinit var agencyId: String
    private lateinit var productList: ArrayList<Product>
    private lateinit var cartItemList: ArrayList<CartItem>
    private var totalAmt = 0.0f
    private val TAX_PERCENT = 0.08f
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMyCartBinding.inflate(layoutInflater)
        setContentView(binding.root)
        database = FirebaseDatabase.getInstance().reference

        agencyId = intent.getStringExtra("agencyId")!!

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        // populate Recycle View
        binding.rvCartProducts.layoutManager = LinearLayoutManager(applicationContext)
        binding.rvCartProducts.setHasFixedSize(true)
        readCart()

        binding.btnCheckOut.setOnClickListener {

            if (cartItemList.isNotEmpty()) {
//                val intent = Intent(this, MyCartActivity::class.java)
//                intent.putExtra("totalAmt", agencyId)
//                startActivity(intent)
            } else {
                Toast.makeText(this, "Cart is empty!", Toast.LENGTH_LONG).show()
            }

        }

    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
    }

    fun readCart() {
        val userId = "USERID"

        database.child("Cart").child(userId).child(agencyId).addValueEventListener(object :
            ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                if (dataSnapshot.exists()) {
                    // get cart Item List
                    cartItemList = arrayListOf<CartItem>()
                    for (cartItem in dataSnapshot.children) {
                        // TODO: handle the post
                        Log.d("TAG", cartItem.key + "")

                        cartItemList.add(CartItem(cartItem.key!!, cartItem.getValue(Long::class.java)!!))
                    }

                    // get products of cart
                    if (cartItemList.isNotEmpty()) {
                        productList = arrayListOf<Product>()
                        database.child("Products").addValueEventListener(object :
                            ValueEventListener {
                            override fun onDataChange(snapshot: DataSnapshot) {
                                if (snapshot.exists()) {
                                    for (product in snapshot.children) {
                                        val prod = product.getValue(Product::class.java)
                                        if (prod != null) {
                                            for (cartItem in cartItemList) {
                                                if (cartItem.productId == prod.id) {
                                                    productList.add(prod!!)
                                                }
                                            }
                                        }
                                    }
                                    // set UI
                                    var subTotal = 0.0f
                                    var taxedAmt = 0.0f
                                    var total = 0.0f
                                    if (productList.isNotEmpty()) {
                                        for (prod in productList) {
                                            for (item in cartItemList) {
                                                if (item.productId == prod.id) {
                                                    subTotal += prod.price * item.qty
                                                break
                                                }
                                            }
                                        }
                                        // calc tax
                                        taxedAmt = subTotal * TAX_PERCENT
                                        total = subTotal + taxedAmt
                                        totalAmt = total
                                    }
                                    binding.tvSubTotalAmount.text = "RM$subTotal"
                                    binding.tvTaxAmount.text = "RM$taxedAmt"
                                    binding.tvTotalAmount.text = "RM$total"

                                    val adapter = CartAdapter(productList, cartItemList, this@MyCartActivity)
                                    binding.rvCartProducts.adapter = adapter
                                }
                            }

                            override fun onCancelled(error: DatabaseError) {
                                // Getting Post failed, log a message
                                Log.w("TAG", "loadPost:onCancelled", error.toException())
                            }
                        })
                    }

                }
            }

            override fun onCancelled(databaseError: DatabaseError) {
                // Getting Post failed, log a message
                Log.w("TAG", "loadPost:onCancelled", databaseError.toException())
                // ...
            }
        })
    }

    override fun itemClick(position: Int) {
        TODO("Not yet implemented")
    }
}