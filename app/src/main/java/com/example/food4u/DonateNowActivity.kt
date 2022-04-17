package com.example.food4u

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.borjabravo.readmoretextview.ReadMoreTextView
import com.example.food4u.databinding.ActivityCreateEventBinding
import com.example.food4u.databinding.ActivityDonateNowBinding
import com.example.food4u.databinding.ActivityDonorListBinding
import com.example.food4u.modal.Events
import com.example.food4u.modal.Notification
import com.example.food4u.modal.User
import com.google.firebase.database.*
import com.squareup.picasso.Picasso
import java.text.SimpleDateFormat
import java.util.*
import kotlin.math.roundToInt

class DonateNowActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDonateNowBinding
    private lateinit var database: DatabaseReference
    private lateinit var eventId: String
    private lateinit var event: Events
    lateinit var raisedListener: ValueEventListener
    lateinit var donorListener: ValueEventListener

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDonateNowBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        eventId = intent.getStringExtra("eventId").toString()


        database = FirebaseDatabase.getInstance().reference
//        database.child("Events/$eventId").get().addOnSuccessListener {
//            event = it.getValue(Events::class.java)!!
//
//            binding.tvEventTitle.text = event.eventTitle
//            binding.tvEventsReadMore.text = event.description
//            binding.tvGoalAmt.text = event.goal.toString()
//            binding.tvOraniserName.text = event.organizerName
//            binding.tvOrganiserAddress.text = event.address
//            binding.tvCollectedAmount.text = event.raised.toString()
//            if (event.imageURL.isNotEmpty())
//                Picasso.get().load(event.imageURL).into(binding.imageEvent)
//            val collectedPerc = ((event.raised / event.goal * 100) * 100.0f).roundToInt() / 100.0f
//            binding.tvCollectedPercentage.text = "($collectedPerc %)"
//            binding.progressBar.progress = collectedPerc.roundToInt()
//
//            database.child("userDB/User/${event.organizerId}").get().addOnSuccessListener {
//                val user = it.getValue(User::class.java)!!
//                if (user.imgUrl.toString().isNotEmpty()) {
//                    Picasso.get().load(user.imgUrl.toString()).into(binding.imageOrganiser)
//                }else {
//                    binding.imageOrganiser.setImageResource(R.drawable.userimg)
//                }
//            }
//
//        }


        raisedListener = database.child("Events/$eventId").addValueEventListener(object:
            ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    if (snapshot.exists()) {
                        Log.d("TAG", snapshot.children.toString())
                        val event = snapshot.getValue(Events::class.java)!!

                        binding.tvEventTitle.text = event.eventTitle
                        binding.tvEventsReadMore.text = event.description
                        binding.tvGoalAmt.text = event.goal.toString()
                        binding.tvOraniserName.text = event.organizerName
                        binding.tvOrganiserAddress.text = event.address
                        binding.tvCollectedAmount.text = event.raised.toString()
                        if (event.imageURL.isNotEmpty())
                            Picasso.get().load(event.imageURL).into(binding.imageEvent)
                        val collectedPerc = ((event.raised / event.goal * 100) * 100.0f).roundToInt() / 100.0f
                        binding.tvCollectedPercentage.text = "($collectedPerc %)"
                        binding.progressBar.progress = collectedPerc.roundToInt()

                        database.child("userDB/User/${event.organizerId}").get().addOnSuccessListener {
                            val user = it.getValue(User::class.java)!!
                            if (user.imgUrl.toString().isNotEmpty()) {
                                Picasso.get().load(user.imgUrl.toString()).into(binding.imageOrganiser)
                            }else {
                                binding.imageOrganiser.setImageResource(R.drawable.userimg)
                            }
                        }
                        if (event.complete) {
                            binding.btnDoneNow.isEnabled = false
                        }
                        // Goal reached
                        if (event.raised >= event.goal && !event.complete) {
                            //update event
                            database.child("Events/$eventId/complete").setValue(true)

                            //send noti
                            val key = database.push().key!!
                            val notiTitle = "An event you have participated has reached it's GOAL WOO HOO!!"
                            val notiMsg = "You have helped ${event.eventTitle} reach it's target of RM${event.raised} Thank you so much!"
                            val sdf = SimpleDateFormat("dd/M/yyyy hh:mm:ss")
                            val currentDate = sdf.format(Date())
                            val noti = Notification(key, notiTitle, notiMsg, currentDate)
                            val donorList = arrayListOf<String>()
                            donorListener = database.child("Donor/${event.id}").addValueEventListener( object :
                                ValueEventListener {
                                    override fun onDataChange(snapshot: DataSnapshot) {
                                        for (donor in snapshot.children) {
                                            donorList.add(donor.key.toString())
                                        }

                                        if (donorList.isNotEmpty()) {

                                            val childrenVal = hashMapOf<String, Any?>()
                                            for (donor in donorList) {

                                                database.child("EventPayment/${donor}/userId").get().addOnSuccessListener {
                                                    val userId = it.getValue(String::class.java)!!
                                                    childrenVal.put("/Notification/${noti.id}/", noti.toMap())
                                                    //childrenVal.put("/Notification/${event.id}/donors/", hashMapOf(userId to true))
                                                    childrenVal.put("/UserNotification/${userId}/${noti.id}/", true)
                                                    database.updateChildren(childrenVal)
                                                }

                                            }
                                            //childrenVal.put("/Events/${event.id}/complete", true)


                                            //database.child("Donor/${event.id}").removeEventListener(donorListener)
                                        }
                                    }

                                    override fun onCancelled(error: DatabaseError) {
                                        Log.d("DonorListener", "Cancelled")
                                    }
                                }
                            )
                        }

                    }
                }

                override fun onCancelled(error: DatabaseError) {
                    TODO("Not yet implemented")
                }
            }
        )


        binding.btnDoneNow.setOnClickListener{
            //TODO : Check if raised is equal to goal
            val intentDonateNow = Intent(this, FundRaisingInfoActivity::class.java)
            intentDonateNow.putExtra("eventId", eventId)
            startActivity(intentDonateNow)
        }

        binding.tvRaised.setOnClickListener {
            val intentDonateNow = Intent(this, DonorListActivity::class.java)
            intentDonateNow.putExtra("eventId", eventId)
            startActivity(intentDonateNow)
        }
    }

    fun sendNoti(noti: Notification) {

    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
    }

    override fun onDestroy() {
        super.onDestroy()
        database.child("Events/$eventId").removeEventListener(raisedListener)
    }
}