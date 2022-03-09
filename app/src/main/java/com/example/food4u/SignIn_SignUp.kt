package com.example.food4u

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class SignIn_SignUp : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_in_sign_up)
        supportActionBar?.hide()
    }
}