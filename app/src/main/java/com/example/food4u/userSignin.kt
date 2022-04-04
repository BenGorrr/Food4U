package com.example.food4u

import android.app.Dialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.Window
import android.widget.*
import com.example.food4u.databinding.ActivityUserSigninBinding
import com.example.food4u.firebase.firebaseHelper
import com.google.firebase.auth.FirebaseUser

class userSignin : AppCompatActivity() {
    private lateinit var binding: ActivityUserSigninBinding
    private lateinit var FB: firebaseHelper
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()

        //viewBinding
        binding = ActivityUserSigninBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        val backArrow = binding.backArrow
        val btnConfirmSignIn = binding.btnConfirmSIgnIn
        val tfEmail = binding.tfEmailSignIn
        val tfUserPassword = binding.tfPassword
        val tvForgotPW = binding.tvResetPw
        FB = firebaseHelper(this)

        val user:FirebaseUser? = FB.checkCurrUser()
        val msg:String = user?.email.toString()
        Log.d("check", msg)

        backArrow.setOnClickListener() {
            val intent = Intent(this, SignInSignUp::class.java)
            startActivity(intent)
        }

        btnConfirmSignIn.setOnClickListener() {
            if (tfEmail.getText().toString().isEmpty() || tfUserPassword.getText().toString()
                    .isEmpty()
            ) {
                if (tfEmail.getText().toString().isEmpty()) {
                    tfEmail.setError("Your email is required!")
                } else if (tfUserPassword.getText().toString().isEmpty()) {
                    tfUserPassword.setError("Your password is required!")
                } else {
                    tfEmail.setError("Your email is required!")
                    tfUserPassword.setError("Your password is required!")
                }
            } else {
                val email = tfEmail.getText().toString()
                val password = tfUserPassword.getText().toString()
                FB.signInUser(email, password)
                    .addOnSuccessListener {
                        Toast.makeText(this, "Sign in successfully!", Toast.LENGTH_SHORT).show()
                        val intent = Intent(this, ProfilePage::class.java)
                        startActivity(intent)
                        finish()
                    }
                    .addOnFailureListener {
                        Toast.makeText(
                            this,
                            "Your email/password is incorrect, please try again",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
            }
        }

        tvForgotPW.setOnClickListener() {
            showResetPwDialog()
        }


    }

    private fun showResetPwDialog() {
        FB = firebaseHelper(this)
        val dialog: Dialog = Dialog(this)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setCancelable(true)
        dialog.setContentView(R.layout.resetpw_dialog)

        val email: EditText = dialog.findViewById(R.id.tfResetEmail)

        val btnConfirm: Button = dialog.findViewById(R.id.btnResetPw)
        val progressBar: ProgressBar = dialog.findViewById(R.id.progressBar)
        progressBar.visibility = View.INVISIBLE

        btnConfirm.setOnClickListener() {
            val emailText: String = email.getText().toString()
            progressBar.visibility = View.VISIBLE
            FB.forgetPw(emailText)
                .addOnCompleteListener(this) {
                    progressBar.visibility = View.GONE
                    if (it.isSuccessful) {
                        Toast.makeText(
                            this,
                            "Your password reset link has been sent to the email.",
                            Toast.LENGTH_SHORT
                        ).show()
                        dialog.dismiss()
                    } else Toast.makeText(this, "Invalid reset email", Toast.LENGTH_SHORT).show()
                }
        }
        dialog.show()
    }

}