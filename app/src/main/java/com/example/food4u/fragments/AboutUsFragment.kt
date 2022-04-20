package com.example.food4u.fragments

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.food4u.ContactUsMainActivity
import com.example.food4u.R
import com.example.food4u.TransactionHistoryActivity
import com.example.food4u.databinding.FragmentAboutUsBinding


class AboutUsFragment : Fragment() {
    private lateinit var binding: FragmentAboutUsBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentAboutUsBinding.inflate(inflater, container, false)

        val btnContactUs = binding.contactUsTv8

        btnContactUs.setOnClickListener {
            val intent = Intent(activity, ContactUsMainActivity::class.java)
            startActivity(intent)
        }

        return binding.root
    }

}