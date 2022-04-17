package com.example.food4u

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.borjabravo.readmoretextview.ReadMoreTextView
import com.example.food4u.databinding.ActivityCreateEventBinding
import com.example.food4u.databinding.ActivityDonateNowBinding
import com.example.food4u.databinding.ActivityDonorListBinding
import com.example.food4u.modal.Events
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.squareup.picasso.Picasso
import kotlin.math.roundToInt

class DonateNowActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDonateNowBinding
    private lateinit var database: DatabaseReference
    private lateinit var eventId: String
    private lateinit var event: Events

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDonateNowBinding.inflate(layoutInflater)
        setContentView(binding.root)

        eventId = intent.getStringExtra("eventId").toString()

        database = FirebaseDatabase.getInstance().getReference("Events/$eventId")
        database.get().addOnSuccessListener {
            event = it.getValue(Events::class.java)!!

            binding.tvEventTitle.text = event.eventTitle
            binding.tvEventsReadMore.text = event.description
            binding.tvGoalAmt.text = event.goal.toString()
            binding.tvOraniserName.text = event.organizerName
            binding.tvOrganiserAddress.text = event.address
            binding.tvCollectedAmount.text = event.raised.toString()
            if (event.imageURL.isNotEmpty())
                Picasso.get().load(event.imageURL).into(binding.imageEvent)
            val collectedPerc = event.raised / event.goal * 100
            binding.tvCollectedPercentage.text = "($collectedPerc %)"
            binding.progressBar.progress = collectedPerc.roundToInt()
        }




        binding.btnDoneNow.setOnClickListener{
            //TODO : Check if raised is equal to goal
            val intentDonateNow = Intent(this, FundRaisingInfoActivity::class.java)
            intentDonateNow.putExtra("eventId", eventId)
            startActivity(intentDonateNow)
        }

        binding.tvRaised.setOnClickListener {
            val intentDonateNow = Intent(this, DonorListActivity::class.java)
            startActivity(intentDonateNow)
        }
    }
}