package com.example.food4u

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.food4u.databinding.ActivityMainBinding
import com.example.food4u.databinding.ActivityPaymentThankYouBinding

class PaymentThankYouActivity : AppCompatActivity() {

    lateinit var binding: ActivityPaymentThankYouBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPaymentThankYouBinding.inflate(layoutInflater)
        setContentView(binding.root)



    }
}