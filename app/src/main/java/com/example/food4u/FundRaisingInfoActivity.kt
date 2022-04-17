package com.example.food4u

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.util.Patterns
import com.example.food4u.databinding.ActivityFundRaisingInfoBinding
import com.example.food4u.modal.Donors
import com.example.food4u.modal.Events
import com.example.food4u.modal.EventPayment
import com.example.food4u.modal.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_fund_raising_info.*
import java.text.SimpleDateFormat
import java.util.*

class FundRaisingInfoActivity : AppCompatActivity() {
    private lateinit var binding: ActivityFundRaisingInfoBinding
    private lateinit var database: DatabaseReference
    private lateinit var eventId: String
    private lateinit var event: Events


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFundRaisingInfoBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val fundraisingDonationAmount = binding.tvFundRaisingAmount;

        eventId = intent.getStringExtra("eventId").toString()

        database = FirebaseDatabase.getInstance().reference
        database.child("Events/$eventId").get().addOnSuccessListener {
            event = it.getValue(Events::class.java)!!

            binding.tvOrganizeName.text = event.organizerName
        }
        database.child("userDB/User").child(FirebaseAuth.getInstance().currentUser!!.uid).get().addOnSuccessListener {
            val user = it.getValue(User::class.java)!!

            binding.inputName.setText(user.name)
            binding.inputEmail.setText(user.email)
            binding.inputPhone.setText(user.phoneNo)
            val sdf = SimpleDateFormat("dd/M/yyyy")
            val currentDate = sdf.format(Date())
            binding.inputDate.setText(currentDate)
            binding.inputDate.isEnabled = false

        }

        binding.btn10.setOnClickListener{
            fundraisingDonationAmount.setText("10")
        }
        binding.btn20.setOnClickListener{
            fundraisingDonationAmount.setText("20")
        }
        binding.btn50.setOnClickListener{
            fundraisingDonationAmount.setText("50")
        }
        binding.btn100.setOnClickListener{
            fundraisingDonationAmount.setText("100")
        }

        binding.btnDonate.setOnClickListener {
            checkValidation()
        }
    }

    private fun checkValidation(){
        val donorName = binding.inputName.text.toString().trim()
        val donorEmail = binding.inputEmail.text.toString().trim()
        val donorPhone = binding.inputPhone.text.toString().trim()
        val symbols = "0123456789/?!:;%"
        val donateDate = binding.inputDate.text.toString().trim()
        val donateAmount = binding.tvFundRaisingAmount.text.toString().trim()
        val donateMessage = binding.donateMessageBox.text.toString().trim()
        var valid=true

        if(donorName.isEmpty()){
            binding.inputName.error = "This field cannot be empty"
            valid=false
        } else if (binding.inputName!!.text.any {it in symbols}) {
            binding.inputName.error = "ERROR! Cannot contains digits/invalid symbols!"
            valid=false
        }

        if(donorEmail.isEmpty()){
            binding.inputEmail.error = "This field cannot be empty"
            valid=false
        } else if (!Patterns.EMAIL_ADDRESS.matcher(donorEmail).matches()) {
            binding.inputEmail.error = "Invalid Email"
            valid=false
        }

        if(donorPhone.isEmpty()){
            binding.inputPhone.error = "This field cannot be empty"
            valid=false
//            if (donorPhone.length != 10) {
//                binding.inputEmail.error = "Invalid Phone Number"
//                valid=false
//            }
//            else if(!Patterns.PHONE.matcher(donorPhone).matches()){
//                binding.inputEmail.error = "Invalid Phone Number"
//                valid=false
//            }
        }

        if(donateDate.isEmpty()){
            binding.inputDate.error = "This field cannot be empty"
            valid=false
        }
        if(donateAmount.isEmpty()){
            binding.tvFundRaisingAmount.error = "This field cannot be empty"
            valid=false
        }
        if(donateMessage.isEmpty()){
            binding.donateMessageBox.error = "This field cannot be empty"
            valid=false
        }

        if(valid){
            database = FirebaseDatabase.getInstance().reference
//            val key = database.push().key!!

//            val newDonor = Donors(key,donorName,donateAmount.toFloat(),donateDate.toString())
//            addNewDonor(newDonor)

            val paymentId = UUID.randomUUID().toString()
            var amt = 0.0f
            amt = binding.tvFundRaisingAmount.text.toString().trim().toFloat()
            val newEventPayment = EventPayment(paymentId, amt, FirebaseAuth.getInstance().currentUser!!.uid, eventId, binding.inputDate.text.toString())

            database.child("EventPayment").child(newEventPayment.id).setValue(newEventPayment).addOnSuccessListener {
                Log.d("EventPayment", "Success")
                val intentFundraisingInfo: Intent = Intent(this, PaymentMethodActivity::class.java)
                intentFundraisingInfo.putExtra("eventPaymentId", paymentId)
                startActivity(intentFundraisingInfo)

            }.addOnFailureListener{
                Log.d("EventPayment", "Failed")
            }


        }
    }


    private fun createOrder(newEventPayment: EventPayment) {
        database.child("EventPayment").child(newEventPayment.id).setValue(newEventPayment).addOnSuccessListener {
            Log.d("EventPayment", "Success")
        }.addOnFailureListener{
            Log.d("EventPayment", "Failed")
        }
    }

    private fun addNewDonor(newDonor:Donors) {
        database.child("Donors").child(newDonor.id).setValue(newDonor)
            .addOnSuccessListener {
                Log.d("Successful","Successful")
            }
            .addOnFailureListener{
                Log.d("Something went wrong", "Error" + it.message)
            }
    }
}