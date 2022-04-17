package com.example.food4u.modal

import android.accounts.AuthenticatorDescription

data class Events(
    var id:String="",
    var organizerName:String ="",
    var address:String="",
    var eventTitle:String="",
    var goal:Float= 0.0f,
    var description: String="",
    var imageURL: String = "",
    var organizerId: String = "",
    var raised: Float=0.0f,
    var complete: Boolean = false,
)
