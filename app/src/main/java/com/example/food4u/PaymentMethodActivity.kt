package com.example.food4u

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.example.food4u.databinding.ActivityPaymentMethodBinding
import com.example.food4u.modal.CreditCard
import com.example.food4u.modal.EventPayment
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_payment_method.*


class PaymentMethodActivity : AppCompatActivity() {
    private lateinit var binding: ActivityPaymentMethodBinding
    lateinit var database: DatabaseReference
    var eventPaymentId = "null"
    var orderPaymentId = "null"
    var userCreditCard = false


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()
        binding = ActivityPaymentMethodBinding.inflate(layoutInflater)
        setContentView(binding.root)

        eventPaymentId = intent.getStringExtra("eventPaymentId").toString()
        orderPaymentId = intent.getStringExtra("orderPaymentId").toString()

        val btnAddCard = binding.btnAddCreditCard
        val paypalImg = binding.imageViewPayPal
        val paypalTv = binding.tvPayPalTittle
        val btnBackPaymentMethod = binding.imageBackAddNewCard
        val creditCardImg = binding.imageViewCreditCard
        val creditCardTv = binding.tvCredirCardTittle
        val paymentErrorTv = binding.paymentErrorMsgTv
        val paymentErrorBg = binding.paymentErrorMsgBg
        val touchNGoImg = binding.imageViewTNG
        val touchNGoTv = binding.tvTNGTittle
        val savedCreditCardBg = binding.savedcreditCardBg

        //GetCard
        database = FirebaseDatabase.getInstance().reference
        database.child("userDB/creditCard").child(Firebase.auth.uid.toString()).get()
            .addOnSuccessListener { rec->
                if(rec!=null){
                    val card = rec.getValue(CreditCard::class.java)
                    if (card != null){
                        savedcreditCardNumber.setText(card.creditCardNo)
                        val viewCard: View = binding.btnAddCreditCard
                        viewCard.setVisibility(View.GONE)
                        val viewCardTv: View = binding.tvAddCardTitle
                        viewCardTv.setVisibility(View.GONE)
                        val savedCardBg: View = binding.savedcreditCardBg
                        savedCardBg.setVisibility(View.VISIBLE)
                        val savedCardNumber: View = binding.savedcreditCardNumber
                        savedCardNumber.setVisibility(View.VISIBLE)
                        val savedMasterCardImg: View = binding.masterCardImg
                        savedMasterCardImg.setVisibility(View.VISIBLE)
                        userCreditCard = true
                    }
                }
            }

        //Display Msg After Card Deleted
        var deletedCard = false
        deletedCard = intent.getBooleanExtra("deleted", deletedCard)
        if (deletedCard) {
            paymentErrorTv.text = "Card Successfully Deleted"
            val view: View = paymentErrorTv
            view.setVisibility(View.VISIBLE)
            view.postDelayed(Runnable { view.setVisibility(View.GONE) }, 3000)
            val viewBg: View = paymentErrorBg
            viewBg.setVisibility(View.VISIBLE)
            viewBg.postDelayed(Runnable { viewBg.setVisibility(View.GONE) }, 3000)
        }

        //Navigate to add new card page
        btnAddCard.setOnClickListener() {
            val intent = Intent(this, AddNewCardActivity::class.java)
            startActivity(intent)
            finish()
        }
        savedCreditCardBg.setOnClickListener() {
            val intent = Intent(this, AddNewCardActivity::class.java)
            startActivity(intent)
            finish()
        }

        //Back to profile page
        btnBackPaymentMethod.setOnClickListener {
            val intent = Intent(this, ProfilePage::class.java)
            startActivity(intent)
            finish()
        }
        //Paypal
        paypalImg.setOnClickListener() {
            val intent = Intent(this, PaypalActivity::class.java)
            if (eventPaymentId != "null")
                intent.putExtra("eventPaymentId", eventPaymentId)
            else if (orderPaymentId != "null")
                intent.putExtra("orderPaymentId", orderPaymentId)
            startActivity(intent)
            finish()
        }
        paypalTv.setOnClickListener() {
            val intent = Intent(this, PaypalActivity::class.java)
            if (eventPaymentId != "null")
                intent.putExtra("eventPaymentId", eventPaymentId)
            else if (orderPaymentId != "null")
                intent.putExtra("orderPaymentId", orderPaymentId)
            startActivity(intent)
            finish()
        }
        //TouchNGo
        touchNGoImg.setOnClickListener(){
            paymentErrorTv.text = "Touch N Go Unavailable"
            val view: View = paymentErrorTv
            view.setVisibility(View.VISIBLE)
            view.postDelayed(Runnable { view.setVisibility(View.GONE) }, 3000)
            val viewBg: View = paymentErrorBg
            viewBg.setVisibility(View.VISIBLE)
            viewBg.postDelayed(Runnable { viewBg.setVisibility(View.GONE) }, 3000)
        }
        touchNGoTv.setOnClickListener(){
            paymentErrorTv.text = "Touch N Go Unavailable"
            val view: View = paymentErrorTv
            view.setVisibility(View.VISIBLE)
            view.postDelayed(Runnable { view.setVisibility(View.GONE) }, 3000)
            val viewBg: View = paymentErrorBg
            viewBg.setVisibility(View.VISIBLE)
            viewBg.postDelayed(Runnable { viewBg.setVisibility(View.GONE) }, 3000)
        }
        //CreditCard
        creditCardImg.setOnClickListener() {
            if (!userCreditCard){
                paymentErrorTv.text = "No Credit Card Available"
                val view: View = paymentErrorTv
                view.setVisibility(View.VISIBLE)
                view.postDelayed(Runnable { view.setVisibility(View.GONE) }, 3000)
                val viewBg: View = paymentErrorBg
                viewBg.setVisibility(View.VISIBLE)
                viewBg.postDelayed(Runnable { viewBg.setVisibility(View.GONE) }, 3000)
            } else {
                val intent = Intent(this, AddNewCardActivity::class.java)
                startActivity(intent)
                finish()
            }
        }
        creditCardTv.setOnClickListener() {
            if (!userCreditCard){
                paymentErrorTv.text = "No Credit Card Available"
                val view: View = paymentErrorTv
                view.setVisibility(View.VISIBLE)
                view.postDelayed(Runnable { view.setVisibility(View.GONE) }, 3000)
                val viewBg: View = paymentErrorBg
                viewBg.setVisibility(View.VISIBLE)
                viewBg.postDelayed(Runnable { viewBg.setVisibility(View.GONE) }, 3000)
            } else {
                val intent = Intent(this, AddNewCardActivity::class.java)
                startActivity(intent)
                finish()
            }
        }
    }

}