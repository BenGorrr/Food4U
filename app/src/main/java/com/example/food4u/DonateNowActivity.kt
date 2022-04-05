package com.example.food4u

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.food4u.databinding.ActivityDonateNowBinding
import com.example.food4u.databinding.ActivityDonorListBinding

class DonateNowActivity : AppCompatActivity() {

    lateinit var binding: ActivityDonateNowBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDonateNowBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}