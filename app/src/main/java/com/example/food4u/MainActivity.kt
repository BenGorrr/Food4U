package com.example.food4u

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.content.Intent
import android.content.SharedPreferences
import android.util.Log
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.food4u.databinding.ActivityMainBinding
import com.example.food4u.firebase.firebaseHelper
import com.example.food4u.fragments.*
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    private lateinit var FB: firebaseHelper
    private lateinit var database: DatabaseReference
    val TAG = "MainActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        FB = firebaseHelper(this)
        val DB =
            Firebase.database("https://food4u-9d1c8-default-rtdb.asia-southeast1.firebasedatabase.app/")
        database = DB.getReference("userDB")
        database.child("User").child(Firebase.auth.uid.toString()).get()
            .addOnSuccessListener { rec ->
                if (rec != null) {
                    if(rec.child("role").value.toString() == "Admin"){
                        val intent = Intent(this, AdminActivity::class.java)
                        startActivity(intent)
                    }
                }
            }

        val homeFragment = HomeFragment()
        val aboutUsFragment = AboutUsFragment()
        val notificationFragment = NotificationFragment()
        val profileFragment = ProfileFragment()
        val fundRaisingFragment = FundRaisingFragment()
        val necessityFragment = NecessityFragment()

        setCurrentFragment(homeFragment)

        nav.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.nav_home -> {
                    setCurrentFragment(homeFragment)
                    Log.i(TAG, "Home selected")
                }
                R.id.nav_abtUs -> {
                    setCurrentFragment(aboutUsFragment)
                    Log.i(TAG, "About Us selected")
                }
                R.id.nav_notification -> {
                    setCurrentFragment(notificationFragment)
                    Log.i(TAG, "Notification selected")
                }
                R.id.nav_profile -> {
//                    setCurrentFragment(profileFragment)
//                    Log.i(TAG, "Profile selected")
                    val intent = Intent(this, ProfilePage::class.java)
                    startActivity(intent)

                }
                R.id.nav_donate -> {
                    setCurrentFragment(necessityFragment)
                    Log.i(TAG, "Necessity selected")
                }
//                R.id.nav_donate -> {
//                    setCurrentFragment(fundRaisingFragment)
//                    Log.i(TAG, "Donate selected")
//                }
            }
            true
        }

    }

    private fun setCurrentFragment(fragment: Fragment) =
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.fl_wrapper, fragment)
            commit()
        }
}






        //val user = getSharedPreferences("PREFERENCE", MODE_PRIVATE).getString("userName", null)
        //val isLogin = getSharedPreferences("PREFERENCE", MODE_PRIVATE).getBoolean("isLogIn", false)

        //fragment views
//        val homeFragment = HomeFragment()
//        val aboutUsFragment:Fragment = AboutUsFragment()
//        val notificationFragment = NotificationFragment()
//        val profileFragment = ProfileFragment()
//
//        //perform click on the bottom bar, but somehow it is not changing the fragments
//        makeCurrentFragment(aboutUsFragment)
//        val bottom_navigation = findViewById<BottomNavigationView>(R.id.bottomNavigationView)
//        bottom_navigation.setOnItemSelectedListener {
//            when(it.itemId){
//                R.id.ic_home -> {
//                    makeCurrentFragment(homeFragment)
//                }
//                R.id.ic_abtUs -> {
//                    makeCurrentFragment(aboutUsFragment)
//                }
//                R.id.ic_notification -> {
//                    makeCurrentFragment(notificationFragment)
//                }
//                R.id.ic_profile -> {
//                    makeCurrentFragment(profileFragment)
//                }
//            }
//            true
//        }

//        if(isLogin){
//            Toast.makeText(this, "Youre logged in!", Toast.LENGTH_SHORT).show()
//            val intent = Intent(this, ProfilePage::class.java)
//            startActivity(intent)
//
//        }else{
//            Toast.makeText(this, "Youre not logged in!", Toast.LENGTH_SHORT).show()
//        }

    // replace fragments
//    private fun makeCurrentFragment(fragment: Fragment) =
//        supportFragmentManager.beginTransaction().apply {
//        replace(R.id.fl_wrapper, fragment)
//        commit()
//    }
//}