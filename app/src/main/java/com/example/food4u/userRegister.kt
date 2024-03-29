package com.example.food4u

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.*
import com.example.food4u.databinding.ActivityUserRegisterBinding
import com.example.food4u.firebase.firebaseHelper
import com.example.food4u.modal.User
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import java.util.*

class userRegister : AppCompatActivity() {
    private lateinit var binding: ActivityUserRegisterBinding
    private lateinit var FB: firebaseHelper
    private lateinit var database:DatabaseReference
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()

        //viewBinding
        binding = ActivityUserRegisterBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        //variables
        val backArrow = binding.backArrow
        val tfEmail = binding.tfEmail
        val tfUsername = binding.tfUsername
        val tfUserPassword = binding.tfPassword
        val tfConfirmPw = binding.tfConfirmPassword
        val cbAgree = binding.cbAgree
        val btnReg = binding.btnConfirmSignUp
        var boolCheck = true

        FB = firebaseHelper(this)
        val DB = Firebase.database("https://food4u-9d1c8-default-rtdb.asia-southeast1.firebasedatabase.app/")
        database= DB.getReference("userDB")

        backArrow.setOnClickListener(){
            val intent = Intent(this, SignInSignUp::class.java)
            startActivity(intent)
        }

        btnReg.setOnClickListener(){
            if(tfEmail.getText().toString().isEmpty()||tfUsername.getText().toString().isEmpty()||tfUserPassword.getText().toString().isEmpty()||tfConfirmPw.getText().toString().isEmpty()||!(cbAgree.isChecked)){
                if(tfEmail.getText().toString().isEmpty()){
                    tfEmail.setError("Your email is required!")
                }
                else if(tfUsername.getText().toString().isEmpty()){
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
                    tfEmail.setError("Your email is required!")
                    tfUsername.setError("Your username is required!")
                    tfUserPassword.setError("Your password is required!")
                    tfConfirmPw.setError("Please re-enter the password!")
                    cbAgree.setError("Please check the box!")
                }
            }
            else{
                var userType=""
                val email = tfEmail.getText().toString()
                if(email.contains("admin", ignoreCase = true)){
                    userType = "Admin"
                }
                else userType = "User"
                val username = tfUsername.getText().toString()
                val password = tfUserPassword.getText().toString()
                val checkPassword = tfConfirmPw.getText().toString()
                if(password.equals(checkPassword) && password.length>=6){
                    FB.registerUser(email, password)
                        .addOnSuccessListener {
                            val newUser = User(email, userType,username,"")
                            database.child("User").child(Firebase.auth.uid.toString()).setValue(newUser)
                            Firebase.auth.signOut()
                            val intent = Intent(this, userSignin::class.java)
                            startActivity(intent)
                        }
                        .addOnFailureListener{
                            // toast not working bruh
                        }
                }
                else{
                    Toast.makeText(this, "Incorrect passwords!",Toast.LENGTH_SHORT).show()
                }
            }
        }

    }

}