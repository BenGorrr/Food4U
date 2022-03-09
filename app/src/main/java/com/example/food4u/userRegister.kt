package com.example.food4u

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.*
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
        val tfUsername: TextView = binding.tfUsername
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
            if(tfUsername.getText().toString().isEmpty()||tfUserPassword.getText().toString().isEmpty()||tfConfirmPw.getText().toString().isEmpty()||!(cbAgree.isChecked)){
                if(tfUsername.getText().toString().isEmpty()){
                    tfUsername.setError("Your username is required!")
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
                    tfUsername.setError("Your username is required!")
                    tfUserPassword.setError("Your password is required!")
                    tfConfirmPw.setError("Please re-enter the password!")
                    cbAgree.setError("Please check the box!")
                }
            }
            else{
                val username = tfUsername.getText().toString()
                val password = tfUserPassword.getText().toString()
                val checkPassword = tfConfirmPw.getText().toString()
                if(password.equals(checkPassword)){
                    val checkUser:Boolean = DB.checkusername(username)
                    if(!checkUser){
                        val insertUser:Boolean = DB.insertData(username, password)
                        if(insertUser){
                            Toast.makeText(this, "Registered successfully!",Toast.LENGTH_SHORT).show()
                            val intent = Intent(this, userSignin::class.java)
                            startActivity(intent)
                        }
                        else{
                            Toast.makeText(this, "Registered failed!",Toast.LENGTH_SHORT).show()
                        }
                    }
                    else{
                        Toast.makeText(this, "User already exists! Please sign in instead.",Toast.LENGTH_SHORT).show()
                    }
                }
                else{
                    Toast.makeText(this, "Incorrect passwords!",Toast.LENGTH_SHORT).show()
                }
            }
        }

    }


}