package com.example.food4u

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.example.food4u.databinding.ActivityChangePasswordBinding
import com.example.food4u.firebase.firebaseHelper
import com.example.food4u.fragments.ChangePasswordPreFragment
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.EmailAuthProvider

import com.google.firebase.auth.AuthCredential

class ChangePasswordActivity : AppCompatActivity() {
    private lateinit var FB: firebaseHelper
    private lateinit var binding: ActivityChangePasswordBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityChangePasswordBinding.inflate(layoutInflater)
        supportActionBar?.hide()
        setContentView(binding.root)

        val backArr = binding.backArr

        replaceFragment(ChangePasswordPreFragment())

        backArr.setOnClickListener{
            val intent = Intent(this, ProfilePage::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION)
            startActivity(intent)
            finish()
        }

    }

    private fun replaceFragment(fragment1: Fragment) {
        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.container, fragment1)
        fragmentTransaction.commit()
    }
}