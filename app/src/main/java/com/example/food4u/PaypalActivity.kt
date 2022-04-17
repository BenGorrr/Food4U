package com.example.food4u

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.annotation.RequiresApi
import com.example.food4u.databinding.ActivityPaymentMethodBinding
import com.example.food4u.databinding.ActivityPaypalBinding
import com.paypal.checkout.PayPalCheckout.setConfig
import com.paypal.checkout.PayPalCheckout
import com.paypal.checkout.approve.OnApprove
import com.paypal.checkout.config.CheckoutConfig
import com.paypal.checkout.config.Environment
import com.paypal.checkout.config.SettingsConfig
import com.paypal.checkout.createorder.CreateOrder
import com.paypal.checkout.createorder.CurrencyCode
import com.paypal.checkout.createorder.OrderIntent
import com.paypal.checkout.createorder.UserAction
import com.paypal.checkout.order.Amount
import com.paypal.checkout.order.AppContext
import com.paypal.checkout.order.Order
import com.paypal.checkout.order.PurchaseUnit
import kotlinx.android.synthetic.main.activity_payment_method.*
import kotlinx.android.synthetic.main.activity_paypal.*

class PaypalActivity : AppCompatActivity() {
    private lateinit var binding: ActivityPaypalBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPaypalBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val btnPaypal = binding.payPalButton
        val btncancel = binding.cancelBtnPaypal

        btncancel.setOnClickListener() {
            val intent = Intent(this, PaymentMethodActivity::class.java)
            startActivity(intent)
            finish()
        }

        val config = CheckoutConfig(
            application = this.application,
            clientId = "AaDoDK3r899KhjvlmMtvL8uvLHsPptzaZDn_ScxRcpugcDYU0RQ1Mf-T39ZJLuFWsWMg_sLVuaBm5aVf",
            environment = Environment.SANDBOX,
            returnUrl = "com.example.food4u://paypalpay",
            currencyCode = CurrencyCode.MYR,
            userAction = UserAction.PAY_NOW,
            settingsConfig = SettingsConfig(
                loggingEnabled = true
            )
        )
        setConfig(config)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            payPalButton.setup(
                createOrder =
                CreateOrder { createOrderActions ->
                    val order =
                        Order(
                            intent = OrderIntent.CAPTURE,
                            appContext = AppContext(userAction = UserAction.PAY_NOW),
                            purchaseUnitList =
                            listOf(
                                PurchaseUnit(
                                    amount =
                                    Amount(currencyCode = CurrencyCode.MYR, value = "10.00")
                                )
                            )
                        )
                    createOrderActions.create(order)
                },
                onApprove =
                OnApprove { approval ->
                    approval.orderActions.capture { captureOrderResult ->
                        Log.i("CaptureOrder", "CaptureOrderResult: $captureOrderResult")
                    }
                }
            )
        }
    }
}