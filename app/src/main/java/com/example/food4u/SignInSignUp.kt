package com.example.food4u

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.food4u.databinding.ActivitySignInSignUpBinding

class SignInSignUp : AppCompatActivity() {
    private lateinit var binding: ActivitySignInSignUpBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()

        //viewBinding
        binding = ActivitySignInSignUpBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //components
        val btnSignIn = binding.btnSignIn
        val btnSignUp = binding.btnSignUp

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