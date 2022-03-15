package com.example.food4u

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.content.Intent
import android.content.SharedPreferences
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.food4u.fragments.AboutUsFragment
import com.example.food4u.fragments.HomeFragment
import com.example.food4u.fragments.NotificationFragment
import com.example.food4u.fragments.ProfileFragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val isLogin = getSharedPreferences("PREFERENCE", MODE_PRIVATE).getBoolean("isLogIn", false)

        //fragment views
        val homeFragment = HomeFragment()
        val aboutUsFragment:Fragment = AboutUsFragment()
        val notificationFragment = NotificationFragment()
        val profileFragment = ProfileFragment()

        //perform click on the bottom bar, but somehow it is not changing the fragments
        makeCurrentFragment(aboutUsFragment)
        val bottom_navigation = findViewById<BottomNavigationView>(R.id.bottomNavigationView)
        bottom_navigation.setOnItemSelectedListener {
            when(it.itemId){
                R.id.ic_home -> {
                    makeCurrentFragment(homeFragment)
                }
                R.id.ic_abtUs -> {
                    makeCurrentFragment(aboutUsFragment)
                }
                R.id.ic_notification -> {
                    makeCurrentFragment(notificationFragment)
                }
                R.id.ic_profile -> {
                    makeCurrentFragment(profileFragment)
                }
            }
            true
        }

        if(isLogin){
            Toast.makeText(this, "Youre logged in!", Toast.LENGTH_SHORT).show()

        }else{
            Toast.makeText(this, "Youre not logged in!", Toast.LENGTH_SHORT).show()
        }
    }

    // replace fragments
    private fun makeCurrentFragment(fragment: Fragment) =
        supportFragmentManager.beginTransaction().apply {
        replace(R.id.fl_wrapper, fragment)
        commit()
    }
}