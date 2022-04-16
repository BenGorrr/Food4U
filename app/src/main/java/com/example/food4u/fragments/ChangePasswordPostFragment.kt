package com.example.food4u.fragments

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.food4u.ChangePasswordActivity
import com.example.food4u.ProfilePage
import com.example.food4u.R
import com.example.food4u.SignInSignUp
import com.example.food4u.databinding.FragmentChangePasswordPostBinding
import com.example.food4u.databinding.FragmentChangePasswordPreBinding
import com.example.food4u.firebase.firebaseHelper
import com.google.firebase.auth.EmailAuthProvider
import com.google.firebase.auth.FirebaseUser

class ChangePasswordPostFragment : Fragment() {
    private lateinit var binding: FragmentChangePasswordPostBinding
    private lateinit var FB: firebaseHelper
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentChangePasswordPostBinding.inflate(inflater, container, false)

        val btnConfirm = binding.btnConfirmChange
        btnConfirm.setOnClickListener{
            val tfNewPw = binding.tfNewPw.text.toString()
            val tfNewPwConfirm = binding.tfNewPwConfirm.text.toString()
            if(tfNewPw.equals(tfNewPwConfirm) && tfNewPw.length>=6){
                FB = firebaseHelper(context)
                val user: FirebaseUser? = FB.checkCurrUser()
                if(user!=null){
                   user.updatePassword(tfNewPw)
                       .addOnSuccessListener {
                           Log.d("check","password changed")
                           Handler(Looper.getMainLooper()).postDelayed({
                               val intent = Intent(activity, ProfilePage::class.java)
                               intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION)
                               startActivity(intent)
                               activity!!.finish()
                           }, 1500)
                       }
                }
            }
            else{
                Toast.makeText(context, "Your passwords are invalid!",Toast.LENGTH_SHORT).show() //doesnt show
            }

        }

        return binding.root
    }

}