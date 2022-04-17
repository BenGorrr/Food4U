package com.example.food4u.fragments

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.food4u.FundRaisingInfoActivity
import com.example.food4u.R
import com.example.food4u.databinding.FragmentFundRaisingBinding
import com.example.food4u.databinding.FragmentNecessityBinding


class FundRaisingFragment : Fragment() {

    private lateinit var binding: FragmentFundRaisingBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentFundRaisingBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // do listener and func here
        binding.btnCreateEvent.setOnClickListener {
//            val intentCreateEvent: Intent = Intent(this, FundRaisingInfoActivity::class.java)
//            startActivity(intentCreateEvent)
        }

    }
}