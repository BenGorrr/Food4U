package com.example.food4u

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.widget.Toast
import com.example.food4u.firebase.firebaseHelper
import com.google.firebase.auth.FirebaseUser

class SplahScreen : AppCompatActivity() {
    private lateinit var FB: firebaseHelper
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splah_screen)
        supportActionBar?.hide()
        //to check signin signup, uncomment this line
        //getSharedPreferences("PREFERENCE", MODE_PRIVATE).edit().putBoolean("isFirstRun", true).commit()
        //val isFirstRun = getSharedPreferences("PREFERENCE", MODE_PRIVATE).getBoolean("isFirstRun", true)

        FB = firebaseHelper(this)
        Handler(Looper.getMainLooper()).postDelayed({
            val user: FirebaseUser? = FB.checkCurrUser()
            if(user!=null){
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
                finish()
            }else{
                val intent = Intent(this, SignInSignUp::class.java)
                startActivity(intent)
                finish()
            }
//            if (isFirstRun) {
//                //show start activity
//                getSharedPreferences("PREFERENCE", MODE_PRIVATE).edit().putBoolean("isFirstRun", false).commit()
//                startActivity(Intent(this, SignInSignUp::class.java))
//                Toast.makeText(this, "First Run", Toast.LENGTH_LONG).show()
//            }else{
//                val isLogin = getSharedPreferences("PREFERENCE", MODE_PRIVATE).getBoolean("isLogIn", false)
//                if(isLogin){
//                    Toast.makeText(this, "Youre logged in!", Toast.LENGTH_SHORT).show()
//                    val intent = Intent(this, MainActivity::class.java)
//                    startActivity(intent)
//                    //this line to un-login
//                    getSharedPreferences("PREFERENCE", MODE_PRIVATE).edit().putBoolean("isLogIn", false).commit()
//                }else{
//                    Toast.makeText(this, "Youre not logged in!", Toast.LENGTH_SHORT).show()
//                    val intent = Intent(this, SignInSignUp::class.java)
//                    startActivity(intent)
//                }
//
//            }
        }, 1500)
        finish()

    }
}