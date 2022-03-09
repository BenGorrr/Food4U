package com.example.food4u

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import com.example.food4u.databinding.ActivityUserSigninBinding
import com.example.food4u.sql.DatabaseHelper

class userSignin : AppCompatActivity() {
    private lateinit var binding: ActivityUserSigninBinding
    private lateinit var DB: DatabaseHelper
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_signin)
        supportActionBar?.hide()

        binding = DataBindingUtil.setContentView(this, R.layout.activity_user_signin)
        val backArrow:ImageView = binding.backArrow
        val btnConfirmSignIn:Button = binding.btnConfirmSIgnIn
        val tfUserEmail:TextView = binding.tfEmailAddress
        val tfUserPassword:TextView = binding.tfPassword
        DB = DatabaseHelper(this)

        backArrow.setOnClickListener(){
            val intent = Intent(this, SignIn_SignUp::class.java)
            startActivity(intent)
        }

        btnConfirmSignIn.setOnClickListener(){
            if(tfUserEmail.getText().toString().isEmpty()||tfUserPassword.getText().toString().isEmpty()){
                if(tfUserEmail.getText().toString().isEmpty()){
                    tfUserEmail.setError("Your email is required!")
                }
                else if(tfUserPassword.getText().toString().isEmpty()){
                    tfUserPassword.setError("Your password is required!")
                }
                else{
                    tfUserEmail.setError("Your email is required!")
                    tfUserPassword.setError("Your password is required!")
                }
            }
            else{
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
            }
        }
    }
}