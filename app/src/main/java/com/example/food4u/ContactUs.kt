package com.example.food4u

import android.R
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.food4u.databinding.ActivityContactUsBinding
import com.example.food4u.databinding.FragmentContactUsMainBinding
import android.content.DialogInterface
import android.net.Uri
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.core.view.isVisible


class ContactUs : AppCompatActivity() {
    lateinit var binding: FragmentContactUsMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()
        binding = FragmentContactUsMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val btnContactUs = binding.btnSendUsMessage
        val successfulMsgTv = binding.successfulMsg
        val successfulMsgBg = binding.successfulMsgBg
        val telPhoneNumber = binding.addressLocation3

        btnContactUs.setOnClickListener() {
            val intent = Intent(this, ContactUsActivity::class.java)
            startActivity(intent)
            finish()
        }

        telPhoneNumber.setOnClickListener(){
            val intent = Intent(Intent.ACTION_CALL, Uri.parse("tel:" + "603 2234 1123"))
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