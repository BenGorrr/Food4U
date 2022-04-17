package com.example.food4u

import android.content.Intent
import android.media.metrics.Event
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.annotation.RequiresApi
import com.example.food4u.databinding.ActivityPaymentMethodBinding
import com.example.food4u.databinding.ActivityPaypalBinding
import com.example.food4u.modal.Donors
import com.example.food4u.modal.EventPayment
import com.example.food4u.modal.OrderPayment
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ServerValue
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
    private lateinit var database: DatabaseReference
    var eventPaymentId = ""
    var orderPaymentId = ""
    var amount = "0.0"

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


        eventPaymentId = intent.getStringExtra("eventPaymentId").toString()
        orderPaymentId = intent.getStringExtra("orderPaymentId").toString()
        var eventPayment: EventPayment = EventPayment()
        var orderPayment: OrderPayment = OrderPayment()
        if (eventPaymentId != "null") {
            database = FirebaseDatabase.getInstance().reference
            database.child("EventPayment/$eventPaymentId").get().addOnSuccessListener {
                eventPayment = it.getValue(EventPayment::class.java)!!
                amount = eventPayment.amount.toString()
            }
        }
        else if (orderPaymentId != "null") {
            database = FirebaseDatabase.getInstance().reference
            database.child("OrderPayment/$orderPaymentId").get().addOnSuccessListener {
                orderPayment = it.getValue(OrderPayment::class.java)!!
                amount = orderPayment.amount.toString()
            }
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
                                    Amount(currencyCode = CurrencyCode.MYR, value = amount)
                                )
                            )
                        )
                    createOrderActions.create(order)
                },
                onApprove =
                OnApprove { approval ->
                    approval.orderActions.capture { captureOrderResult ->
                        Log.i("CaptureOrder", "CaptureOrderResult: $captureOrderResult")


                        if (eventPaymentId != "null") {
                            database.child("EventPayment/$eventPaymentId/complete").setValue(true)
                                .addOnSuccessListener {
                                    Log.d("Payment", "Success")

                                    //val newDonor = Donors(key, donorName, donateAmount.toFloat(), donateDate.toString())
                                    //                            database.child("Donors").child(newDonor.id).setValue(newDonor)
                                    //                                .addOnSuccessListener {
                                    //                                    Log.d("Successful","Successful")
                                    //                                }
                                    //                                .addOnFailureListener{
                                    //                                    Log.d("Something went wrong", "Error" + it.message)
                                    //                                }
                                    var raised = 0.0f
                                    database.child("Events/${eventPayment.eventId}/raised").get().addOnSuccessListener {
                                        raised = it.getValue(Float::class.java)!! + eventPayment.amount

                                        val childUpdate = hashMapOf<String, Any>(
                                            "/Donor/${eventPayment.eventId}/${eventPaymentId}" to true,
                                            "/Events/${eventPayment.eventId}/raised" to raised
                                        )
                                        database.updateChildren(childUpdate)
                                        database.child("userDB/User/${eventPayment.userId}/numOfDonation").setValue(ServerValue.increment(1))
                                    }

                                    val intent = Intent(this, PaymentThankYouActivity::class.java)
                                    intent.putExtra("eventId", eventPayment.eventId)
                                    startActivity(intent)
                                    finish()
                                }.addOnFailureListener {
                                Log.d("Payment", "Failed")
                                finish()
                            }
                        } else if (orderPaymentId != "null") {
                            database.child("OrderPayment/$orderPaymentId/complete").setValue(true)
                                .addOnSuccessListener {
                                    Log.d("Payment", "Success")

                                    //val newDonor = Donors(key, donorName, donateAmount.toFloat(), donateDate.toString())
                                    //                            database.child("Donors").child(newDonor.id).setValue(newDonor)
                                    //                                .addOnSuccessListener {
                                    //                                    Log.d("Successful","Successful")
                                    //                                }
                                    //                                .addOnFailureListener{
                                    //                                    Log.d("Something went wrong", "Error" + it.message)
                                    //                                }
                                    val childUpdate = hashMapOf<String, Any?>(
                                        "/Cart/${orderPayment.userId}/${orderPayment.agencyId}" to null,
                                    )
                                    database.updateChildren(childUpdate)

                                    val intent = Intent(this, PaymentThankYouActivity::class.java)
                                    intent.putExtra("agencyId", orderPayment.agencyId)
                                    startActivity(intent)
                                    finish()
                                }.addOnFailureListener {
                                    Log.d("Payment", "Failed")
                                    finish()
                                }
                        }
                    }
                }
            )
        }
    }

    private fun addNewDonor(newDonor: Donors) {
        database.child("Donors").child(newDonor.id).setValue(newDonor)
            .addOnSuccessListener {
                Log.d("Successful","Successful")
            }
            .addOnFailureListener{
                Log.d("Something went wrong", "Error" + it.message)
            }
    }
}