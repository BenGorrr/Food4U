package com.example.food4u

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.CheckBox
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import com.example.food4u.databinding.ActivityUserRegisterBinding
import com.example.food4u.databinding.ActivityUserSigninBinding
import com.example.food4u.sql.DatabaseHelper

class userRegister : AppCompatActivity() {
    private lateinit var binding: ActivityUserRegisterBinding
    private lateinit var DB: DatabaseHelper
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_register)
        supportActionBar?.hide()

        binding = DataBindingUtil.setContentView(this, R.layout.activity_user_register)
        val backArrow: ImageView = binding.backArrow
        val tfUserEmail: TextView = binding.tfEmailAddress
        val tfUserPassword: TextView = binding.tfPassword
        val tfConfirmPw: TextView = binding.tfConfirmPassword
        val cbAgree:CheckBox = binding.cbAgree
        val btnReg: Button = binding.btnConfirmSignUp
        DB = DatabaseHelper(this)

        backArrow.setOnClickListener(){
            val intent = Intent(this, SignIn_SignUp::class.java)
            startActivity(intent)
        }

        btnReg.setOnClickListener(){
            if(tfUserEmail.getText().toString().isEmpty()||tfUserPassword.getText().toString().isEmpty()||tfConfirmPw.getText().toString().isEmpty()||!(cbAgree.isChecked)){
                if(tfUserEmail.getText().toString().isEmpty()){
                    tfUserEmail.setError("Your email is required!")
                }
                else if(tfUserPassword.getText().toString().isEmpty()){
                    tfUserPassword.setError("Your password is required!")
                }
                else if(tfConfirmPw.getText().toString().isEmpty()){
                    tfConfirmPw.setError("Please re-enter the password!")
                }
                else if(!(cbAgree.isChecked)){
                    cbAgree.setError("Please check the box!")
                }
                else{
                    tfUserEmail.setError("Your email is required!")
                    tfUserPassword.setError("Your password is required!")
                    tfConfirmPw.setError("Please re-enter the password!")
                    cbAgree.setError("Please check the box!")
                }
            }
            else{
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
            }
        }

    }


}