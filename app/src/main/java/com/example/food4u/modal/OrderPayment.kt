package com.example.food4u.modal

data class OrderPayment(
    var id: String = "",
    var amount: Float = 0.0f,
    var userId: String = "",
    var agencyId: String = "",
    var date: String = "",
    var complete: Boolean = false,
)
