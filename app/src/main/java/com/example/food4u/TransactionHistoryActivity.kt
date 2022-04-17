package com.example.food4u

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.food4u.databinding.ActivityTransactionHistoryBinding
import com.example.food4u.firebase.firebaseHelper

class TransactionHistoryActivity : AppCompatActivity() {
    private lateinit var binding: ActivityTransactionHistoryBinding
    private lateinit var FB: firebaseHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()
        binding = ActivityTransactionHistoryBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val btnTransactionHistoryBack = binding.btnBackTransactionHistory

        btnTransactionHistoryBack.setOnClickListener(){
            val intent = Intent(this, ProfilePage::class.java)
            startActivity(intent)
            finish()
        }


    }
}