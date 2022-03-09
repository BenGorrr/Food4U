package com.example.food4u

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.Toast

class SplahScreen : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splah_screen)
        supportActionBar?.hide()
        //to check signin signup, uncomment this line
        //getSharedPreferences("PREFERENCE", MODE_PRIVATE).edit().putBoolean("isFirstRun", true).commit()

        val isFirstRun = getSharedPreferences("PREFERENCE", MODE_PRIVATE).getBoolean("isFirstRun", true)
        Handler(Looper.getMainLooper()).postDelayed({
            if (isFirstRun) {
                //show start activity
                getSharedPreferences("PREFERENCE", MODE_PRIVATE).edit().putBoolean("isFirstRun", false).commit()
                startActivity(Intent(this, SignIn_SignUp::class.java))
                Toast.makeText(this, "First Run", Toast.LENGTH_LONG).show()
            }else{
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
            }
        }, 1500)

    }
}