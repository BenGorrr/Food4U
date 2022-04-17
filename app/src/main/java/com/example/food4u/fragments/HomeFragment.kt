package com.example.food4u.fragments

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.food4u.FundRaisingInfoActivity
import com.example.food4u.R
import com.example.food4u.databinding.FragmentHomeBinding
import com.google.firebase.FirebaseApiNotAvailableException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase


class HomeFragment : Fragment() {
    private lateinit var binding: FragmentHomeBinding

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
    }
    private fun setCurrentFragment(fragment: Fragment) =
        activity!!.supportFragmentManager.beginTransaction().apply {
            replace(R.id.fl_wrapper, fragment)
            commit()
        }
}