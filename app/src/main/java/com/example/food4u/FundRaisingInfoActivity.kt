package com.example.food4u

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Patterns
import com.example.food4u.databinding.ActivityFundRaisingInfoBinding
import com.example.food4u.databinding.ActivityPaymentThankYouBinding
import java.text.SimpleDateFormat
import java.util.*
import java.util.regex.Pattern

class FundRaisingInfoActivity : AppCompatActivity() {
    lateinit var binding: ActivityFundRaisingInfoBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFundRaisingInfoBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val fundraisingDonationAmount = binding.tvFundRaisingAmount;


        binding.btn10.setOnClickListener{
            fundraisingDonationAmount.setText("10")
        }
        binding.btn20.setOnClickListener{
            fundraisingDonationAmount.setText("20")
        }
        binding.btn50.setOnClickListener{
            fundraisingDonationAmount.setText("50")
        }
        binding.btn100.setOnClickListener{
            fundraisingDonationAmount.setText("100")
        }

        binding.btnDonate.setOnClickListener {
            val donorName = binding.inputName.text.toString().trim();
            val donorEmail = binding.inputEmail.text.toString().trim();
            val donorPhone = binding.inputPhone.text.toString().trim();
            val symbols = "0123456789/?!:;%"
            val donateDate = binding.inputDate.text.toString().trim();

            if(donorName.isEmpty()){
                binding.inputName.error = "This field cannot be empty"
            }else if (binding.inputName!!.text.any {it in symbols}) {
                binding.inputName.error = "ERROR! Cannot contains digits/invalid symbols!"
            }

            if(donorEmail.isEmpty()){
                binding.inputEmail.error = "This field cannot be empty"
            } else if (!Patterns.EMAIL_ADDRESS.matcher(donorEmail).matches()) {
                binding.inputEmail.error = "Invalid Email"
            }

            if(donorPhone.isEmpty()){
                binding.inputPhone.error = "This field cannot be empty"

                if (donorPhone.length != 10) {
                    binding.inputEmail.error = "Invalid Phone Number"
                }
                else if(!Patterns.PHONE.matcher(donorPhone).matches()){
                    binding.inputEmail.error = "Invalid Phone Number"
                }
            }

            if(donateDate.isEmpty()){
                binding.inputDate.error = "This field cannot be empty"
            }
        }
    }



}