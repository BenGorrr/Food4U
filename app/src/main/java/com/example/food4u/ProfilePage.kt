package com.example.food4u

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.TextView
import androidx.activity.result.contract.ActivityResultContract
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import com.example.food4u.databinding.ActivityProfilePageBinding
import com.example.food4u.firebase.firebaseHelper
import com.example.food4u.fragments.EditProfileFragment
import com.example.food4u.fragments.ProfileFragment
import com.example.food4u.modal.User
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.database.ktx.getValue
import com.google.firebase.ktx.Firebase
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_profile_page.*

class ProfilePage : AppCompatActivity() {
    private lateinit var binding: ActivityProfilePageBinding
    private lateinit var FB: firebaseHelper
    private lateinit var database: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProfilePageBinding.inflate(layoutInflater)
        supportActionBar?.hide()
        setContentView(binding.root)

        val tvMyProfile = binding.tvMyProfile
        val tvEditProfile = binding.tvEditProfile
        val tvName = binding.tvUserDisplayName
        val profPic = binding.profPic
        val editIcon = binding.editIcon
        val btnPaymentMethod = binding.btnManagePayment
        val numOfDonation = binding.tvNumOfDonation
        val backArr = binding.editBackArr
        var setClick = false

        tvEditProfile.visibility = View.GONE
        backArr.visibility = View.INVISIBLE


        val DB =
            Firebase.database("https://food4u-9d1c8-default-rtdb.asia-southeast1.firebasedatabase.app/")
        database = DB.getReference("userDB")
        FB = firebaseHelper(this)


        replaceFragment(ProfileFragment())

        val user: FirebaseUser? = FB.checkCurrUser()
        val msg: String = user?.email.toString()

        database.child("User").child(Firebase.auth.uid.toString()).get()
            .addOnSuccessListener { rec ->
                if (rec != null) {
                    if(rec.child("imgUrl").value.toString().isEmpty()) profPic.setImageResource(R.drawable.userimg)
                    //else Picasso.get().load(rec.child("imgUrl").value.toString()).resize(150, 150).centerCrop().into(profPic)
                    //tvName.text = rec.child("name").value.toString()
                    //numOfDonation.text = "Donation: " + rec.child("numOfDonation").value.toString()
                } else tvName.text = "Cannot locate username."
            }

        val postListener = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val post = dataSnapshot.getValue<User>()
                if(post!=null){
                    tvName.text = post.name
                    Picasso.get().load(post.imgUrl).resize(150, 150).centerCrop().into(profPic)
                    numOfDonation.text = "Donation: " + post.numOfDonation
                }

//                if(post!=null){
//                    Picasso.get().load(post).resize(150, 150).centerCrop().into(profPic)
//                }
                // ...
            }

            override fun onCancelled(databaseError: DatabaseError) {
                // Getting Post failed, log a message
                Log.w("TAG", "loadPost:onCancelled", databaseError.toException())
            }
        }
        database.child("User").child(Firebase.auth.uid.toString()).addValueEventListener(postListener)

        editIcon.setOnClickListener {
            tvMyProfile.visibility = View.INVISIBLE
            tvEditProfile.visibility = View.VISIBLE
            editIcon.visibility = View.GONE
            tvName.visibility = View.INVISIBLE
            btnPaymentMethod.visibility = View.INVISIBLE
            numOfDonation.visibility = View.INVISIBLE
            backArr.visibility = View.VISIBLE
            profPic.setImageResource(R.drawable.editicon)
            setClick = true
            replaceFragment(EditProfileFragment())
        }

        backArr.setOnClickListener {
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

    override fun onDestroy() {
        super.onDestroy()
    }

}