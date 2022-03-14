package com.example.food4u

import android.app.Dialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.Window
import android.widget.*
import androidx.databinding.DataBindingUtil
import com.example.food4u.databinding.ActivityUserSigninBinding
import com.example.food4u.sql.DatabaseHelper
import android.preference.PreferenceManager

import android.content.SharedPreferences




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
        val tfUsername:TextView = binding.tfUsernameSignIn
        val tfUserPassword:TextView = binding.tfPassword
        val tvForgotPW:TextView = binding.tvResetPw
        DB = DatabaseHelper(this)

        backArrow.setOnClickListener(){
            val intent = Intent(this, SignIn_SignUp::class.java)
            startActivity(intent)
        }

        btnConfirmSignIn.setOnClickListener(){
            if(tfUsername.getText().toString().isEmpty()||tfUserPassword.getText().toString().isEmpty()){
                if(tfUsername.getText().toString().isEmpty()){
                    tfUsername.setError("Your username is required!")
                }
                else if(tfUserPassword.getText().toString().isEmpty()){
                    tfUserPassword.setError("Your password is required!")
                }
                else{
                    tfUsername.setError("Your email is required!")
                    tfUserPassword.setError("Your password is required!")
                }
            }
            else{
                val username = tfUsername.getText().toString()
                val password = tfUserPassword.getText().toString()

                val checkPassword:Boolean = DB.checkusernamepassword(username, password)
                if(checkPassword){
                    Toast.makeText(this, "Login successfully!", Toast.LENGTH_SHORT).show()
                    val isLogin = getSharedPreferences("PREFERENCE", MODE_PRIVATE).edit().putBoolean("isLogIn", true).commit()
//                    val pref = getSharedPreferences()
//                    val prefs = PreferenceManager.getDefaultSharedPreferences(this)
//                    prefs.edit().putBoolean("Islogin", Islogin).commit()
                    val intent = Intent(this, MainActivity::class.java)
                    startActivity(intent)
                }else{
                    Toast.makeText(this, "Invalid login attempt!", Toast.LENGTH_SHORT).show()
                }
            }
        }
        val password = tfUserPassword.getText().toString()
        tvForgotPW.setOnClickListener(){
            showResetPwDialog()
        }

    }

    private fun showResetPwDialog() {
        val dialog:Dialog = Dialog(this)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setCancelable(true)
        dialog.setContentView(R.layout.resetpw_dialog)

        val username:EditText = dialog.findViewById(R.id.tfResetUsername)
        val newPw:EditText = dialog.findViewById(R.id.tfNewPassword)
        val btnConfrim:Button = dialog.findViewById(R.id.btnResetPw)
        btnConfrim.setOnClickListener(){
            val username:String = username.getText().toString()
            val newPass:String = newPw.getText().toString()
            if(DB.changePassword(username, newPass)) {
                dialog.dismiss()
                Toast.makeText(this, "Your password has been changed successfully!", Toast.LENGTH_SHORT).show()
            }else{
                newPw.setError("Your password is same as the old one!")
            }

        }
        dialog.show()
    }
}