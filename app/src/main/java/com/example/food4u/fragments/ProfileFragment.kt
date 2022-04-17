package com.example.food4u.fragments

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.food4u.TransactionHistoryActivity
import com.example.food4u.databinding.FragmentProfileBinding
import com.example.food4u.firebase.firebaseHelper
import com.example.food4u.userSignin
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import java.time.format.DateTimeFormatter

class ProfileFragment : Fragment() {

    private lateinit var binding: FragmentProfileBinding
    private lateinit var database: DatabaseReference
    private lateinit var FB: firebaseHelper
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentProfileBinding.inflate(inflater, container, false)

        val displayEmail = binding.tvDisplayEmail
        val displayBirthDate = binding.tvDisplayDate
        val displayAdd = binding.tvDisplayAddress
        val displayPhoneNo = binding.tvDisplayPhoneNo
        val btnSignOut =  binding.btnSignOut
        val btnTransactionHistory = binding.btnTransactionHistory

        val DB = Firebase.database("https://food4u-9d1c8-default-rtdb.asia-southeast1.firebasedatabase.app/")
        database= DB.getReference("userDB")
        FB = firebaseHelper(activity)

        database.child("User").child(Firebase.auth.uid.toString()).get()
            .addOnSuccessListener { rec->
                if(rec!=null){
                    displayEmail.text = rec.child("email").value.toString()
                    if(rec.child("birthDate").value.toString()!=""){ // if there is date record
                        displayBirthDate.text = rec.child("birthDate").value.toString()
                    }

                    displayAdd.text = rec.child("address").value.toString()
                    displayPhoneNo.text = rec.child("phoneNo").value.toString()
                }
            }

        btnSignOut.setOnClickListener {
            val intent = Intent(activity, userSignin::class.java)
            intent.putExtra("finish", true)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP // To clean up all activities
            startActivity(intent)
            FB.signOut()
            activity!!.finish()
        }

        binding.btnTransactionHistory.setOnClickListener {
            val intent = Intent(activity, TransactionHistoryActivity::class.java)
            intent.putExtra("finish", true)
            startActivity(intent)
        }

        return binding.root
    }



}