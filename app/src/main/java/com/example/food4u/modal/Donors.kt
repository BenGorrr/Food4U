package com.example.food4u.modal

import java.sql.Date
import java.sql.Time

data class Donors(
    var id:String="",
    var donorName:String="",
    var paymentAmount:Float= 0.0f,
    var donateDate:String=""
)
