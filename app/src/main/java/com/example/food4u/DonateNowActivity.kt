package com.example.food4u

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.food4u.databinding.ActivityCreateEventBinding
import com.example.food4u.databinding.ActivityDonateNowBinding
import com.example.food4u.databinding.ActivityDonorListBinding
import com.google.firebase.database.DatabaseReference

class DonateNowActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDonateNowBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDonateNowBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnDoneNow.setOnClickListener{
            val intentDonateNow: Intent = Intent(this, FundRaisingInfoActivity::class.java)
            startActivity(intentDonateNow)
        }

        binding.tvRaised.setOnClickListener {
            val intentDonateNow: Intent = Intent(this, DonorListActivity::class.java)
            startActivity(intentDonateNow)
        }
    }
}