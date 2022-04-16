package com.example.food4u

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.food4u.databinding.ActivityContactUsBinding

class ContactUsActivity : AppCompatActivity() {
    lateinit var binding: ActivityContactUsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()
        binding = ActivityContactUsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val btnBackContactUsActivity = binding.backContactUs2
        val btnSubmitContactUs = binding.btnSendUsMessage2
        val contactUsName = binding.contactUsNameTf
        val contactUsEmail = binding.contactUsEmailTf
        val contactUsMessage = binding.contactUsMessageTf

        btnBackContactUsActivity.setOnClickListener() {
            val intent = Intent(this, ContactUs::class.java)
            startActivity(intent)
            finish()
        }

        btnSubmitContactUs.setOnClickListener() {
            val intent = Intent(this, ContactUs::class.java)
            var b = Bundle()
            b.putBoolean("submitted", true)
            intent.putExtras(b)
            startActivity(intent)
            finish()
        }

    }
}