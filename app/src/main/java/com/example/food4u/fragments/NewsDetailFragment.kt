package com.example.food4u.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.food4u.R
import com.example.food4u.databinding.FragmentNewsDetailBinding


class NewsDetailFragment : Fragment() {

    lateinit var binding: FragmentNewsDetailBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        binding = FragmentNewsDetailBinding.inflate(layoutInflater)

        binding.ivBack.setOnClickListener {
            val homeFragment = HomeFragment.newInstance("profile")
            setCurrentFragment(homeFragment)
        }

        return binding.root
    }

    private fun setCurrentFragment(fragment: Fragment) =
        activity!!.supportFragmentManager.beginTransaction().apply {
            replace(R.id.fl_wrapper, fragment)
            commit()
        }
}