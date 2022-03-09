package com.example.food4u

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import androidx.databinding.DataBindingUtil
import com.example.food4u.databinding.ActivitySignInSignUpBinding

class SignIn_SignUp : AppCompatActivity() {
    private lateinit var binding: ActivitySignInSignUpBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_in_sign_up)
        supportActionBar?.hide()
        binding = DataBindingUtil.setContentView(this, R.layout.activity_sign_in_sign_up)
        //components

        val btnSignIn:Button = binding.btnSignIn
        val btnSignUp:Button = binding.btnSignUp

        btnSignIn.setOnClickListener(){
            val intent = Intent(this, userSignin::class.java)
            startActivity(intent)
        }
        btnSignUp.setOnClickListener(){
            val intent = Intent(this, userRegister::class.java)
            startActivity(intent)
        }

    }
}