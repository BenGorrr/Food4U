package com.example.food4u

import android.R.attr
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.annotation.Nullable
import androidx.annotation.RequiresApi
import com.example.food4u.databinding.ActivityPaymentMethodBinding
import com.paypal.checkout.PayPalCheckout
import com.paypal.checkout.approve.OnApprove
import com.paypal.checkout.cancel.OnCancel
import com.paypal.checkout.config.CheckoutConfig
import com.paypal.checkout.config.Environment
import com.paypal.checkout.config.SettingsConfig
import com.paypal.checkout.createorder.CreateOrder
import com.paypal.checkout.createorder.UserAction
import kotlinx.android.synthetic.main.activity_payment_method.*
import com.paypal.checkout.createorder.CurrencyCode
import com.paypal.checkout.createorder.OrderIntent
import com.paypal.checkout.error.OnError
import com.paypal.checkout.order.Amount
import com.paypal.checkout.order.AppContext
import com.paypal.checkout.order.Order
import com.paypal.checkout.order.PurchaseUnit
import com.paypal.checkout.paymentbutton.PayPalButton

import org.json.JSONException

import org.json.JSONObject


class PaymentMethodActivity : AppCompatActivity() {
    private lateinit var binding: ActivityPaymentMethodBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()
        binding = ActivityPaymentMethodBinding.inflate(layoutInflater)
        setContentView(binding.root)

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

        btnAddCard.setOnClickListener() {
            val intent = Intent(this, AddNewCardActivity::class.java)
            startActivity(intent)
            finish()
        }

        btnBackPaymentMethod.setOnClickListener {
            val intent = Intent(this, ProfilePage::class.java)
            startActivity(intent)
            finish()
        }
        //Paypal
        paypalImg.setOnClickListener() {
            val intent = Intent(this, PaypalActivity::class.java)
            startActivity(intent)
            finish()
        }
        paypalTv.setOnClickListener() {
            val intent = Intent(this, PaypalActivity::class.java)
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
            val creditCardDetail = ""
            if (creditCardDetail.isEmpty()){
                paymentErrorTv.text = "No Credit Card Available"
                val view: View = paymentErrorTv
                view.setVisibility(View.VISIBLE)
                view.postDelayed(Runnable { view.setVisibility(View.GONE) }, 3000)
                val viewBg: View = paymentErrorBg
                viewBg.setVisibility(View.VISIBLE)
                viewBg.postDelayed(Runnable { viewBg.setVisibility(View.GONE) }, 3000)
            }
        }
        creditCardTv.setOnClickListener() {
            val creditCardDetail = ""
            if (creditCardDetail.isEmpty()){
                paymentErrorTv.text = "No Credit Card Available"
                val view: View = paymentErrorTv
                view.setVisibility(View.VISIBLE)
                view.postDelayed(Runnable { view.setVisibility(View.GONE) }, 3000)
                val viewBg: View = paymentErrorBg
                viewBg.setVisibility(View.VISIBLE)
                viewBg.postDelayed(Runnable { viewBg.setVisibility(View.GONE) }, 3000)
            }
        }
    }

}