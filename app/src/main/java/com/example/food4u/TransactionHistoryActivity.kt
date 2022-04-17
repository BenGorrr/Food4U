package com.example.food4u

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.food4u.adapter.NotificationAdapter
import com.example.food4u.adapter.TransactionHistoryAdapter
import com.example.food4u.databinding.ActivityTransactionHistoryBinding
import com.example.food4u.firebase.firebaseHelper
import com.example.food4u.modal.EventPayment
import com.example.food4u.modal.Events
import com.example.food4u.modal.Notification
import com.example.food4u.modal.TransactionHistory
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*

class TransactionHistoryActivity : AppCompatActivity(), TransactionHistoryAdapter.onItemClickListener {
    private lateinit var binding: ActivityTransactionHistoryBinding
    private lateinit var database: DatabaseReference
    private lateinit var transactionList: ArrayList<TransactionHistory>
    private lateinit var transactionIdList: ArrayList<String>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()
        binding = ActivityTransactionHistoryBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val btnTransactionHistoryBack = binding.btnBackTransactionHistory

        btnTransactionHistoryBack.setOnClickListener(){
            finish()
        }

        database = FirebaseDatabase.getInstance().reference


        val TAG = "TransactionHistory"
        database = FirebaseDatabase.getInstance().reference
        val userId = FirebaseAuth.getInstance().uid.toString()

        database.child("EventPayment").addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                transactionList = arrayListOf()
                var totalDonation = 0.0f
                for (data in dataSnapshot.children) {
                    val eventPayment = data.getValue(EventPayment::class.java)!!
                    if (eventPayment.userId == userId) {
                        database.child("Events/${eventPayment.eventId}").get().addOnSuccessListener {
                            val event = it.getValue(Events::class.java)!!
                            val transaction = TransactionHistory(event.eventTitle, eventPayment.amount, eventPayment.date)

                            transactionList.add(transaction)
                            val adapter = TransactionHistoryAdapter(transactionList, this@TransactionHistoryActivity)
                            binding.rvTransactionHistory.adapter = adapter

                            totalDonation += transaction.amount
                            binding.totalAmountDonation.text = "RM ${totalDonation}"
                        }

                    }
                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })

        binding.rvTransactionHistory.layoutManager = LinearLayoutManager(this)
        binding.rvTransactionHistory.setHasFixedSize(true)

//        database.child("EventPayment").child(userId).get().addOnSuccessListener {
//
//
//
////            transactionIdList = arrayListOf()
////            for (id in it.children) {
////                database.child("EventPayment/${id.key.toString()}").get().addOnSuccessListener {
////                    val eventPayment = it.getValue(EventPayment::class.java)!!
////
////                    database.child("Events/${eventPayment.eventId}").get().addOnSuccessListener {
////                        val event = it.getValue(Events::class.java)!!
////                        val transaction = TransactionHistory(event.eventTitle, eventPayment.amount, eventPayment.date)
////
////                        transactionList.add(transaction)
////
////                        val adapter = TransactionHistoryAdapter(transactionList, this@TransactionHistoryActivity)
////                        binding.rvTransactionHistory.adapter = adapter
////                    }
////                }
////            }
//        }
    }

    override fun itemClick(position: Int) {

    }
}