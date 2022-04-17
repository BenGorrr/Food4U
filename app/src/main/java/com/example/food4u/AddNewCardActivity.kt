package com.example.food4u

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import com.example.food4u.databinding.ActivityAddNewCardBinding
import com.example.food4u.modal.CreditCard
import com.example.food4u.modal.Product
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_add_new_card.*
import kotlinx.android.synthetic.main.activity_add_new_card.view.*
import java.util.ArrayList

class AddNewCardActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAddNewCardBinding
    lateinit var database: DatabaseReference

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
        val checkBox = binding.checkBox
        var recentCardNumber = ""
        var recentExpiryDate = ""
        var recentCvv = ""
        var recentCardOwnerName = ""
        val errorMsgBg = binding.errorMsgBg
        val errorMsgTv = binding.ErrorMsgTv

        database = FirebaseDatabase.getInstance().reference
        database.child("userDB/creditCard").child(Firebase.auth.uid.toString()).get()
            .addOnSuccessListener { rec->
                if(rec!=null){
                    val card = rec.getValue(CreditCard::class.java)
                    if (card != null){
                        recentCardNumber = card.creditCardNo
                        recentExpiryDate = card.expiryDate
                        recentCvv = card.cvvNo
                        recentCardOwnerName = card.cardName

                        cardNumbers.setText(card.creditCardNo)
                        expiryDate.setText(card.expiryDate)
                        cvv.setText(card.cvvNo)
                        cardOwnerName.setText(card.cardName)
                        checkBox.checkBox!!.isChecked=true
                    }
                }
            }

        btnBackAddNewCard.setOnClickListener() {
            val intent = Intent(this, PaymentMethodActivity::class.java)
            startActivity(intent)
            finish()
        }

        btnAddCard.setOnClickListener() {
            var cardValid = true

            if (cardNumbers.text.toString().isEmpty()) {
                binding.editTextNumber2.error = "Card is empty"
                cardValid = false
            } else if (cardNumbers.text.toString().length != 16){
                binding.editTextNumber2.error = "Invalid Card Number"
                cardValid = false
            }
            if (expiryDate.text.toString().isEmpty()) {
                binding.editTextDate.error = "Enter Expiry Date"
                cardValid = false
            }

            if (cvv.text.toString().isEmpty()) {
                binding.editTextNumberDecimal2.error = "Enter Card CVV"
                cardValid = false
            } else if(cvv.text.toString().length != 3 && cvv.text.toString().length != 4){
                binding.editTextNumberDecimal2.error = "Invalid Card CVV"
                cardValid = false
            }

            if (cardOwnerName.text.toString().isEmpty()) {
                binding.editTextTextPersonName.error = "Enter Card Name"
                cardValid = false
            }

            if (recentCardNumber == cardNumbers.text.toString() && recentExpiryDate == expiryDate.text.toString() && recentCvv == cvv.text.toString() && recentCardOwnerName == cardOwnerName.text.toString()){
                val viewBg: View = errorMsgBg
                viewBg.setVisibility(View.VISIBLE)
                viewBg.postDelayed(Runnable { viewBg.setVisibility(View.GONE) }, 2000)
                val viewTv: View = errorMsgTv
                viewTv.setVisibility(View.VISIBLE)
                viewTv.postDelayed(Runnable { viewTv.setVisibility(View.GONE) }, 2000)
                cardValid = false
            }

            if (cardValid){
                val card = CreditCard(cardNumbers.text.toString(), expiryDate.text.toString(), cvv.text.toString(), cardOwnerName.text.toString())
                addNewCard(card)

                val intent = Intent(this, PaymentMethodActivity::class.java)
                startActivity(intent)
                finish()
            }
        }

    }
    private fun addNewCard(newCard: CreditCard) {
        database = FirebaseDatabase.getInstance().reference
        database.child("userDB/creditCard").child(Firebase.auth.uid.toString()).setValue(newCard)
            .addOnSuccessListener {
                Log.d("Add CreditCard", "Done")
            }
            .addOnFailureListener {
                Log.d("Add CreditCard", "Error" + it.message)
            }
    }
}