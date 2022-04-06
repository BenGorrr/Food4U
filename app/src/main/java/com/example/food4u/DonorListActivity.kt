package com.example.food4u

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.food4u.databinding.ActivityDonorListBinding
import com.example.food4u.databinding.ActivityFundRaisingInfoBinding

class DonorListActivity : AppCompatActivity() {
    lateinit var binding: ActivityDonorListBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDonorListBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}