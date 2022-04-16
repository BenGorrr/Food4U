package com.example.food4u.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.food4u.R
import com.example.food4u.databinding.FragmentChangePasswordPreBinding
import com.example.food4u.databinding.FragmentEditProfileBinding
import com.example.food4u.firebase.firebaseHelper
import com.google.firebase.auth.EmailAuthProvider
import com.google.firebase.auth.FirebaseUser

class ChangePasswordPreFragment : Fragment() {
    private lateinit var binding: FragmentChangePasswordPreBinding
    private lateinit var FB: firebaseHelper
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentChangePasswordPreBinding.inflate(inflater, container, false)

        val btnNext = binding.btnNext
        btnNext.setOnClickListener{
            val oldPw = binding.tfOldPw
            if(oldPw.text.toString().isEmpty()){
                oldPw.setError("Please don't leave this field empty!")
            }
            else{
                val oldPwString = oldPw.text.toString()
                FB = firebaseHelper(context)
                val user: FirebaseUser? = FB.checkCurrUser()
                if(user!=null){
                    val email = user.email.toString()
                    val credential = EmailAuthProvider.getCredential(email, oldPwString)
                    user.reauthenticate(credential)
                        .addOnSuccessListener{
                            replaceFragment(ChangePasswordPostFragment())
                        }
                        .addOnFailureListener {
                            oldPw.setError("Incorrect password entered.")
                        }
                }
            }

        }

        return binding.root
    }

    private fun replaceFragment(fragment1: Fragment) {
        val fragmentManager = activity!!.supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.container, fragment1)
        fragmentTransaction.commit()
    }

}