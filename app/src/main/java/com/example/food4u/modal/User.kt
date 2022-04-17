package com.example.food4u.modal

import android.graphics.Bitmap
import android.net.Uri
import java.util.*

data class User(
    var email: String="",
    var role: String="",
    var name: String="",
    var birthDate: String = "",
    var address: String = "",
    var phoneNo: String = "",
    var numOfDonation: Int = 0,
    var imgUrl: String = ""
    )
