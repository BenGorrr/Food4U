package com.example.food4u.firebase

import android.content.Context
import android.graphics.Bitmap
import android.util.Log
import com.example.food4u.modal.User
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser

class firebaseHelper(context: Context?) {
    var auth = FirebaseAuth.getInstance()


    fun registerUser(email:String, password: String): Task<AuthResult> {
        return auth.createUserWithEmailAndPassword(email, password)
    }

    fun signInUser(email:String, password: String): Task<AuthResult> {
        return auth.signInWithEmailAndPassword(email, password)
    }

    fun forgetPw(email: String): Task<Void>{
        return auth.sendPasswordResetEmail(email)
    }

    fun signOut(){
        auth.signOut()
    }

    fun checkCurrUser():FirebaseUser?{
        if(auth.currentUser!=null)
            return auth.currentUser
        else return null
    }

}