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
        val isFirstRun = getSharedPreferences("PREFERENCE", MODE_PRIVATE)
            .getBoolean("isFirstRun", true)

        if (isFirstRun) {
            //show start activity
            startActivity(Intent(this@MainActivity, SignIn_SignUp::class.java))
            Toast.makeText(this@MainActivity, "First Run", Toast.LENGTH_LONG).show()
        }
        getSharedPreferences("PREFERENCE", MODE_PRIVATE).edit().putBoolean("isFirstRun", false).commit()
    }
}