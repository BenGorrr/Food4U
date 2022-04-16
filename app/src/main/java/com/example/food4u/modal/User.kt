package com.example.food4u.modal

import android.graphics.Bitmap
import android.net.Uri
import java.util.*

data class User(
    var email: String="",
    var name: String="",
    var birthDate: Date? = null,
    var address: String = "",
    var phoneNo: String = "",
    var numOfDonation: Int = 0,
    var imgUrl: String = "")
