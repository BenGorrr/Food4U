package com.example.food4u

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.food4u.databinding.ActivityAddNewCardBinding

class AddNewCardActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAddNewCardBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()
        binding = ActivityAddNewCardBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val btnBackAddNewCard = binding.imageBackAddNewCard
        val btnAddCard = binding.btnAddCard
        val cardNumbers = binding.editTextNumber2
        val expiryDate = binding.editTextDate
        val cvv = binding.editTextNumberDecimal2
        val cardOwnerName = binding.editTextTextPersonName

        btnBackAddNewCard.setOnClickListener() {
            val intent = Intent(this, PaymentMethodActivity::class.java)
            startActivity(intent)
            finish()
        }

        btnAddCard.setOnClickListener() {
            if (cardNumbers.getText().toString().isEmpty()) {
                binding.editTextNumber2.error = "Card is empty"
            } else if (cardNumbers.getText().toString().length != 16){
                binding.editTextNumber2.error = "Invalid Card Number"
            }

            if (expiryDate.getText().toString().isEmpty()) {
                binding.editTextDate.error = "Enter Expiry Date"
            }

            if (cvv.getText().toString().isEmpty()) {
                binding.editTextNumberDecimal2.error = "Enter Card CVV"
            } else if(cvv.getText().toString().length != 3 && cvv.getText().toString().length != 4){
                binding.editTextNumberDecimal2.error = "Invalid Card CVV"
            }

            if (cardOwnerName.getText().toString().isEmpty()) {
                binding.editTextTextPersonName.error = "Enter Card Name"
            }
        }


    }
}