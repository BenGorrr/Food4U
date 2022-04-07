package com.example.food4u.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.food4u.R
import com.example.food4u.adapter.AgencyAdapter
import com.example.food4u.databinding.FragmentNecessityBinding
import com.example.food4u.modal.Agency
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class NecessityFragment : Fragment(), AgencyAdapter.onItemClickListener {

    private lateinit var binding: FragmentNecessityBinding
    private lateinit var database: DatabaseReference
    private val agencyList = listOf(
        Agency("1","Agency1", "description of this agency"),
        Agency("2","Agency2", "description of this agency"),
        Agency("3","Agency3", "description of this agency")
    )
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentNecessityBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val db = Firebase.database("https://food4u-9d1c8-default-rtdb.asia-southeast1.firebasedatabase.app")
        database = db.reference

        val adapter = AgencyAdapter(agencyList, this)

        binding.recyclerViewAgency.adapter = adapter
        binding.recyclerViewAgency.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerViewAgency.setHasFixedSize(true)


        binding.btnAddAgent.setOnClickListener {
            val key = database.push().key!!
            database.child("agency").child(key).setValue(Agency(key, "Orphanage School", "desc"))
                .addOnFailureListener() {
                    Log.d("Add Agency", "Error" + it.message)
                }
                .addOnSuccessListener  {
                    Log.d("Add Agency", "Done")
                }
        }
    }

    override fun itemClick(position: Int) {
        val selectedAgency = agencyList[position]
        Toast.makeText(requireContext(), selectedAgency.name, Toast.LENGTH_LONG).show()
    }
}