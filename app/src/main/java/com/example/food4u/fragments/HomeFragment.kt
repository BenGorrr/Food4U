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


class HomeFragment : Fragment() {
    private lateinit var binding: FragmentHomeBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentHomeBinding.inflate(layoutInflater)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

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