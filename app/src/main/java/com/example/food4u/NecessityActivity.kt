package com.example.food4u

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.food4u.adapter.AgencyAdapter
import com.example.food4u.adapter.ProductsAdapter
import com.example.food4u.databinding.ActivityNecessityBinding
import com.example.food4u.fragments.NecessityFragment
import com.example.food4u.modal.*
import com.google.android.gms.tasks.OnFailureListener
import com.google.android.gms.tasks.OnSuccessListener
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.UploadTask
import java.util.*

class NecessityActivity : AppCompatActivity(), ProductsAdapter.onItemClickListener {

    lateinit var binding: ActivityNecessityBinding
    lateinit var database: DatabaseReference
    lateinit var agencyId: String
    var selectedName = ""
    private lateinit var productList : ArrayList<Product>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNecessityBinding.inflate(layoutInflater)
        setContentView(binding.root)
        database = FirebaseDatabase.getInstance().getReference("Products")

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val agencyName = intent.getStringExtra("agencyName")
        agencyId = intent.getStringExtra("agencyId")!!

        val mContext = this

        binding.btnAddProduct.setOnClickListener {
//            val key = database.push().key!!
//            addNewProduct(Product(key.toString(), "awd"))
            database = FirebaseDatabase.getInstance().getReference("agencyProducts")
        }

        binding.imageBtnCart.setOnClickListener {
            val intent = Intent(this, MyCartActivity::class.java)
            intent.putExtra("agencyId", agencyId)
            startActivity(intent)
        }

        binding.rvProducts.layoutManager = LinearLayoutManager(applicationContext)
        binding.rvProducts.setHasFixedSize(true)
        //readAllProduct(mContext, agencyId)
        readAgencyProduct(mContext, agencyId)
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
    }

    override fun itemClick(position: Int) {
        //val selectedProduct = productList[position]
        //Toast.makeText(applicationContext, selectedProduct.name, Toast.LENGTH_LONG).show()
//        selectedName = selectedProduct.name
//        startForResult.launch("image/*")
//        database = FirebaseDatabase.getInstance().getReference("agencyProducts")
//        val agencyProd = AgencyProduct(hashMapOf(selectedProduct.id to true))
//        //database.child(agencyId).setValue(agencyProd)
//
//        val agencyProdValues = hashMapOf<String, Boolean>(selectedProduct.id to true)
//        val prodId = selectedProduct.id
//        val childUpdates = hashMapOf<String, Any>(
//            "/$agencyId/products/$prodId" to true,
//        )
//
//        database.updateChildren(childUpdates)

    }

    override fun onAddToCart(product: Product, qty: Int) {
        database = FirebaseDatabase.getInstance().getReference("Cart")
        val userId = FirebaseAuth.getInstance().currentUser!!.uid
        val id = product.id
        val childUpdates = hashMapOf<String, Any>(
            "/$userId/$agencyId/$id" to qty,
        )
        database.updateChildren(childUpdates)
//        database.child(userId).setValue(cart).addOnSuccessListener {
//            Log.d("TAG", "Done add to cart")
//        }
    }

    private fun readAgencyProduct(mContext: NecessityActivity, id: String) {
        val TAG = "Product"
        database = FirebaseDatabase.getInstance().reference

        database.child("agencyProducts").addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                if (dataSnapshot.exists()) {
                    val agencyProductIDList = arrayListOf<String>()
                    for (product in dataSnapshot.children) {
                        // TODO: handle the post
                        if (product.key == id) {
                            val products = product.getValue(AgencyProduct::class.java)
                            if (products != null) {
                                for (prodId in products.products.keys) {
                                    agencyProductIDList.add(prodId)
                                }
                            }
                        }
                    }

                    if (agencyProductIDList.isNotEmpty()) {
                        val productList = arrayListOf<Product>()

                        database.child("Products").addValueEventListener(object : ValueEventListener {
                            override fun onDataChange(dataSnapshot: DataSnapshot) {
                                if (dataSnapshot.exists()) {
                                    for (product in dataSnapshot.children) {
                                        // TODO: handle the post
                                        val product = product.getValue(Product::class.java)
                                        if (product != null) {
                                            if (product.id in agencyProductIDList) {
                                                productList.add(product)
                                                Log.d(TAG, "added")
                                            }
                                        }
                                    }

                                    val adapter = ProductsAdapter(productList, mContext)

                                    binding.rvProducts.adapter = adapter
                                }
                            }

                            override fun onCancelled(databaseError: DatabaseError) {
                                // Getting Post failed, log a message
                                Log.w(TAG, "loadPost:onCancelled", databaseError.toException())
                                // ...
                            }
                        })

                        val adapter = ProductsAdapter(productList, mContext)

                        binding.rvProducts.adapter = adapter

                    }
                }
            }

            override fun onCancelled(databaseError: DatabaseError) {
                // Getting Post failed, log a message
                Log.w(TAG, "loadPost:onCancelled", databaseError.toException())
                // ...
            }
        })
    }

    private fun readAllProduct(mContext: NecessityActivity, id: String) {
        val TAG = "Product"
        database = FirebaseDatabase.getInstance().getReference("Products")

        database.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                if (dataSnapshot.exists()) {
                    productList = arrayListOf<Product>()
                    for (product in dataSnapshot.children) {
                        // TODO: handle the post
                        val product = product.getValue(Product::class.java)
                        productList.add(product!!)
                        Log.d(TAG, "added")
                    }

                    val adapter = ProductsAdapter(productList, mContext)

                    binding.rvProducts.adapter = adapter
                }
            }

            override fun onCancelled(databaseError: DatabaseError) {
                // Getting Post failed, log a message
                Log.w(TAG, "loadPost:onCancelled", databaseError.toException())
                // ...
            }
        })
    }

    private fun addNewProduct(newProduct: Product) {
        database.child(newProduct.id).setValue(newProduct)
            .addOnSuccessListener {
                Log.d("Add Agency", "Done")
            }
            .addOnFailureListener {
                Log.d("Add Agency", "Error" + it.message)
            }
    }

    private val startForResult = registerForActivityResult(
        ActivityResultContracts.GetContent()
    ) { uri ->
        uploadToFirebase(uri)
    }

    private fun uploadToFirebase(fileUri: Uri) {
        if (fileUri != null) {
            val filename = UUID.randomUUID().toString() + ".png"

            val refStorage = FirebaseStorage.getInstance().reference.child("images/products/$filename")

            refStorage.putFile(fileUri)
                .addOnSuccessListener (
                    OnSuccessListener<UploadTask.TaskSnapshot> { taskSnapshot ->
                        taskSnapshot.storage.downloadUrl.addOnSuccessListener {
                            val imageUrl = it.toString()

                            val database = FirebaseDatabase.getInstance().getReference("Products").orderByChild("name").equalTo(selectedName).addListenerForSingleValueEvent(
                                object: ValueEventListener {
                                    override fun onDataChange(dataSnapshot: DataSnapshot) {
                                        for (productSnapshot in dataSnapshot.children) {
                                            val user = productSnapshot.getValue(Product::class.java)

                                            productSnapshot.ref.child("imageURL").setValue(imageUrl)
                                                .addOnSuccessListener {
                                                    Log.d("UPDATE", "Success")
                                                }
                                                .addOnFailureListener {
                                                    Log.d("UPDATE", "Error" + it.message)
                                                }
                                        }

                                    }
                                    override fun onCancelled(databaseError: DatabaseError) {
                                        // Getting Data failed, log a message
                                        Log.w("AWD", "LoginData:onCancelled", databaseError.toException())
                                        // ...
                                        Toast.makeText(applicationContext, "error", Toast.LENGTH_LONG).show()
                                    }
                                }
                            )

                        }
                    })
                ?.addOnFailureListener(OnFailureListener { e ->
                    print(e.message)
                })
        }
    }
}