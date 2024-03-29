package com.example.food4u.fragments

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.food4u.FundRaisingInfoActivity
import com.example.food4u.R
import com.example.food4u.databinding.FragmentHomeBinding
import com.example.food4u.firebase.firebaseHelper
import com.google.firebase.FirebaseApiNotAvailableException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.squareup.picasso.Picasso


class HomeFragment : Fragment() {
    private lateinit var binding: FragmentHomeBinding
    private lateinit var FB: firebaseHelper
    private lateinit var database: DatabaseReference

    companion object {
        const val ARG_NAME = "mode"

        fun newInstance(mode: String): HomeFragment {
            val fragment = HomeFragment()

            val bundle = Bundle().apply {
                putString(ARG_NAME, mode)
            }

            fragment.arguments = bundle

            return fragment
        }
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentHomeBinding.inflate(layoutInflater)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val choosePanel = binding.tvDropPanel
        val tvHey = binding.textView14
        val tvName = binding.tvHomeName
        val tvDon = binding.tvUserDonationAmount
        val userImg = binding.imageView10
        val mainUserPanel = binding.mainUserPanel
        val DB =
            Firebase.database("https://food4u-9d1c8-default-rtdb.asia-southeast1.firebasedatabase.app/")
        database = DB.getReference("userDB")
        FB = firebaseHelper(activity)
        val user: FirebaseUser? = FB.checkCurrUser()
        database.child("User").child(Firebase.auth.uid.toString()).get()
            .addOnSuccessListener { rec ->
                if (rec != null) {
                    tvName.text = rec.child("name").value.toString()
                    tvDon.text = rec.child("numOfDonation").value.toString()
                    if(rec.child("imgUrl").value.toString().isEmpty()) userImg.setImageResource(R.drawable.userimg)
                    else Picasso.get().load(rec.child("imgUrl").value.toString()).resize(150, 150).centerCrop().into(userImg)
                }
            }

        val mode = arguments?.getString(ARG_NAME)
        if(mode!="profile"){
            choosePanel.visibility = View.VISIBLE
            tvHey.visibility = View.GONE
            tvName.visibility = View.GONE
            mainUserPanel.visibility = View.GONE
        }
        else{
            choosePanel.visibility = View.GONE
            tvHey.visibility = View.VISIBLE
            tvName.visibility = View.VISIBLE
            mainUserPanel.visibility = View.VISIBLE
        }
        binding.tvUserName.text = "Friend"
        val uid = FirebaseAuth.getInstance().uid
        val database = FirebaseDatabase.getInstance().reference.child("userDB/User/$uid/name").get().addOnSuccessListener {
            binding.tvUserName.text = it.getValue(String::class.java).toString()
        }



        binding.imageBtnDonate.setOnClickListener {
            setCurrentFragment(FundRaisingFragment())
        }
        binding.imageBtnNecessity.setOnClickListener {
            setCurrentFragment(NecessityFragment())
        }
        binding.imageNavigateNews2.setOnClickListener {
            setCurrentFragment(NewsDetailFragment())
        }
    }
    private fun setCurrentFragment(fragment: Fragment) =
        activity!!.supportFragmentManager.beginTransaction().apply {
            replace(R.id.fl_wrapper, fragment)
            commit()
        }
}