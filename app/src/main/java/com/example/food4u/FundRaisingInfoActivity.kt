package com.example.food4u

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.food4u.databinding.ActivityFundRaisingInfoBinding
import com.example.food4u.databinding.ActivityPaymentThankYouBinding

class FundRaisingInfoActivity : AppCompatActivity() {
    lateinit var binding: ActivityFundRaisingInfoBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFundRaisingInfoBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}