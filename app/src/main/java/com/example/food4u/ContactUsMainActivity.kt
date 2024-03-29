package com.example.food4u

import android.R
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.content.DialogInterface
import android.net.Uri
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.core.view.isVisible
import com.example.food4u.databinding.ActivityContactUsMainBinding
import com.example.food4u.fragments.AboutUsFragment
import com.example.food4u.fragments.HomeFragment


class ContactUsMainActivity : AppCompatActivity() {
    lateinit var binding: ActivityContactUsMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()
        binding = ActivityContactUsMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val btnContactUs = binding.btnSendUsMessage
        val successfulMsgTv = binding.successfulMsg
        val successfulMsgBg = binding.successfulMsgBg
        val telPhoneNumber = binding.addressLocation3
        val findLocation = binding.addressLocation
        val btnBackToAboutUs = binding.backContactUs

        btnContactUs.setOnClickListener() {
            val intent = Intent(this, ContactUsActivity::class.java)
            startActivity(intent)
            finish()
        }

        btnBackToAboutUs.setOnClickListener() {
            val intent = Intent(this, HomeFragment::class.java)
            startActivity(intent)
            finish()
        }

        telPhoneNumber.setOnClickListener(){
            val telNo = Uri.parse("tel:60322341123")
            val intent: Intent = Intent(Intent.ACTION_DIAL, telNo)
            startActivity(intent)
        }

        findLocation.setOnClickListener() {
            val geo = Uri.parse("3.175471140409656, 101.66035248190155")
            val intent: Intent = Intent(Intent.ACTION_VIEW, geo)
            startActivity(intent)
        }

        var submitted = false
        submitted = intent.getBooleanExtra("submitted", submitted)
        if (submitted){
            val view: View = successfulMsgTv
            view.setVisibility(View.VISIBLE)
            view.postDelayed(Runnable { view.setVisibility(View.GONE) }, 3000)
            val viewBg: View = successfulMsgBg
            viewBg.setVisibility(View.VISIBLE)
            viewBg.postDelayed(Runnable { viewBg.setVisibility(View.GONE) }, 3000)
        }

    }

}