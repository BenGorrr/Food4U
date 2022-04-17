package com.example.food4u.modal

data class EventPayment(
    var id: String = "",
    var amount: Float = 0.0f,
    var userId: String = "",
    var eventId: String = "",
    var date: String = "",
    var complete: Boolean = false,
)
