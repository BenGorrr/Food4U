package com.example.food4u

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.content.Intent
import android.content.SharedPreferences
import android.widget.Toast




class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val isLogin = getSharedPreferences("PREFERENCE", MODE_PRIVATE).getBoolean("isLogIn", false)
        if(isLogin){
            Toast.makeText(this, "Youre logged in!", Toast.LENGTH_SHORT).show()
        }else{
            Toast.makeText(this, "Youre not logged in!", Toast.LENGTH_SHORT).show()
        }
    }
}