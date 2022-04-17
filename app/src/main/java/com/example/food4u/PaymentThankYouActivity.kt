package com.example.food4u

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.food4u.databinding.ActivityMainBinding
import com.example.food4u.databinding.ActivityPaymentThankYouBinding
import com.example.food4u.modal.Agency
import com.example.food4u.modal.Events
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class PaymentThankYouActivity : AppCompatActivity() {

    lateinit var binding: ActivityPaymentThankYouBinding
    lateinit var database: DatabaseReference
    var eventId = ""
    var agencyId = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityPaymentThankYouBinding.inflate(layoutInflater)
        setContentView(binding.root)

        database = FirebaseDatabase.getInstance().reference
        eventId = intent.getStringExtra("eventId").toString()
        agencyId = intent.getStringExtra("agencyId").toString()

        if (eventId != "null") {
            database.child("Events").child(eventId).get().addOnSuccessListener {
                val event = it.getValue(Events::class.java)!!
                binding.tvOrganiser.text = event.organizerName
            }
        }else if (agencyId != "null") {
            database.child("agency").child(agencyId).get().addOnSuccessListener {
                val agency = it.getValue(Agency::class.java)!!
                binding.tvOrganiser.text = agency.name
            }
        }


        binding.btnDone.setOnClickListener{
            val intentPaymentTY:Intent = Intent(this, MainActivity::class.java)
            startActivity(intentPaymentTY)
        }

    }
}