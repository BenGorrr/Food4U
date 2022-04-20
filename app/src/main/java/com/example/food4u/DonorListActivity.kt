package com.example.food4u

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.food4u.adapter.CartAdapter
import com.example.food4u.adapter.DonorsAdapter
import com.example.food4u.databinding.ActivityDonorListBinding
import com.example.food4u.databinding.ActivityFundRaisingInfoBinding
import com.example.food4u.modal.CartItem
import com.example.food4u.modal.EventPayment
import com.example.food4u.modal.Events
import com.example.food4u.modal.Product
import com.google.firebase.database.*

class DonorListActivity : AppCompatActivity(), DonorsAdapter.onItemClickListener  {
    lateinit var binding: ActivityDonorListBinding
    lateinit var database: DatabaseReference
    var eventId = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDonorListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        eventId = intent.getStringExtra("eventId").toString()

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        // populate Recycle View
        binding.rvDonorList.layoutManager = LinearLayoutManager(applicationContext)
        binding.rvDonorList.setHasFixedSize(true)


        database = FirebaseDatabase.getInstance().reference
        var event = Events()
        database.child("Events/$eventId").get().addOnSuccessListener {
            event = it.getValue(Events::class.java)!!
            binding.tvAmount.text = "RM${event.raised}"
            binding.tvRemain.text = "RM${event.goal - event.raised}"

            val eventPaymentList = arrayListOf<EventPayment>()

            database.child("Donor/$eventId").addValueEventListener(object :
                ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    if (dataSnapshot.exists()) {
                        //get donor payment list
                        for (donor in dataSnapshot.children) {
                            // TODO: handle the post
                            database.child("EventPayment/${donor.key.toString()}").get().addOnSuccessListener {
                                val eventPayment = it.getValue(EventPayment::class.java)!!
                                eventPaymentList.add(eventPayment)

                                val adapter = DonorsAdapter(eventPaymentList, this@DonorListActivity)
                                binding.rvDonorList.adapter = adapter
                                binding.tvDonorAmount.setText(eventPaymentList.size.toString() + " Donated")
                            }.addOnFailureListener{
                                Log.d("NOP", "FAILED")
                            }
                        }

                    }

                }

                override fun onCancelled(databaseError: DatabaseError) {
                    // Getting Post failed, log a message
                    Log.w("DONOR", "loadPost:onCancelled", databaseError.toException())
                    // ...
                }
            })
        }
        binding.btnDonateNow.setOnClickListener{
            val intentDonorList: Intent = Intent(this, FundRaisingInfoActivity::class.java)
            intentDonorList.putExtra("eventId", eventId)
            startActivity(intentDonorList)
        }

    }

    override fun itemClick(position: Int) {
        TODO("Not yet implemented")
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
    }
}