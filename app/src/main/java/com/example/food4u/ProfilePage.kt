package com.example.food4u

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.food4u.databinding.ActivityProfilePageBinding
import com.example.food4u.firebase.firebaseHelper
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser


class ProfilePage : AppCompatActivity() {
    private lateinit var binding: ActivityProfilePageBinding
    private lateinit var FB: firebaseHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProfilePageBinding.inflate(layoutInflater)
        supportActionBar?.hide()
        setContentView(binding.root)

        val tvName = binding.tvUserDisplayName
        val btnSignOut = binding.btnSignOut
        val btnPaymentMethod = binding.btnManagePayment

        // get from general preference to access user-specific preference
        val username = getSharedPreferences("PREFERENCE", MODE_PRIVATE).getString("username", null)
        val thisUsername = getSharedPreferences("userInfo_"+username, MODE_PRIVATE).getString("userName", null)
        FB = firebaseHelper(this)

        val user: FirebaseUser? = FB.checkCurrUser()
        val msg:String = user?.email.toString()
        Log.d("check", msg)

        tvName.text = thisUsername

        btnSignOut.setOnClickListener{
            val intent = Intent(this, userSignin::class.java)
            intent.putExtra("finish", true)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP // To clean up all activities
            startActivity(intent)

            FB.signOut()
            finish()

        }

        btnPaymentMethod.setOnClickListener {
            val intent = Intent(this, PaymentMethodActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
}