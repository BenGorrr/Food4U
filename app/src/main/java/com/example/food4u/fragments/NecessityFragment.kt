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
import com.google.firebase.database.*
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import androidx.core.app.ActivityCompat.startActivityForResult

import android.content.Intent
import android.net.Uri
import android.provider.MediaStore
import android.renderscript.Sampler
import androidx.activity.result.contract.ActivityResultContracts
import com.example.food4u.NecessityActivity
import com.google.android.gms.tasks.OnFailureListener
import com.google.android.gms.tasks.OnSuccessListener
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.UploadTask
import java.util.*
import kotlin.collections.ArrayList


class NecessityFragment : Fragment(), AgencyAdapter.onItemClickListener {

    private lateinit var binding: FragmentNecessityBinding
    private lateinit var database: DatabaseReference
    private lateinit var agencyList : ArrayList<Agency>
    //private val agencyList = listOf(Agency("awd", "wddd", "asccc"))

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentNecessityBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val mContext = this


        binding.btnAddAgent.setOnClickListener() {
            val key = database.push().key!!
            val agent = Agency(key, "Orphanage School", "desc")
            addNewAgent(agent)
        }

        binding.recyclerViewAgency.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerViewAgency.setHasFixedSize(true)
        readAgency(mContext)

        binding.ivAgentImg.setOnClickListener {
            startForResult.launch("image/*")
        }



//        val TAG = "Agency"
//        val db = Firebase.database("https://food4u-9d1c8-default-rtdb.asia-southeast1.firebasedatabase.app/")
//
//        database.addValueEventListener(object : ValueEventListener {
//            override fun onDataChange(dataSnapshot: DataSnapshot) {
//                if (dataSnapshot.exists()) {
//                    agencyList = arrayListOf<Agency>()
//                    for (agency in dataSnapshot.children) {
//                        // TODO: handle the post
//                        val agent = agency.getValue(Agency::class.java)
//                        agencyList.add(agent!!)
//                        Log.d(TAG, "added")
//                    }
//
//                    val adapter = AgencyAdapter(agencyList, mContext)
//
//                    binding.recyclerViewAgency.adapter = adapter
//                }
//            }
//
//            override fun onCancelled(databaseError: DatabaseError) {
//                // Getting Post failed, log a message
//                Log.w(TAG, "loadPost:onCancelled", databaseError.toException())
//                // ...
//            }
//        })

    }

    private val startForResult = registerForActivityResult(
        ActivityResultContracts.GetContent()
    ) { uri ->
        binding.ivAgentImg.setImageURI(uri)
        uploadToFirebase(uri)
    }

    private fun uploadToFirebase(fileUri: Uri) {
        if (fileUri != null) {
            val filename = UUID.randomUUID().toString() + ".png"

            val refStorage = FirebaseStorage.getInstance().reference.child("images/$filename")

            refStorage.putFile(fileUri)
                .addOnSuccessListener (
                    OnSuccessListener<UploadTask.TaskSnapshot> { taskSnapshot ->
                        taskSnapshot.storage.downloadUrl.addOnSuccessListener {
                            val imageUrl = it.toString()

                            val database = FirebaseDatabase.getInstance().getReference("agency").orderByChild("name").equalTo("Kechara Soup Kitchen").addListenerForSingleValueEvent(
                                object: ValueEventListener {
                                    override fun onDataChange(dataSnapshot: DataSnapshot) {
                                        for (agencySnapshot in dataSnapshot.children) {
                                            val user: Agency? = agencySnapshot.getValue(Agency::class.java)

                                            agencySnapshot.ref.child("imageURL").setValue(imageUrl)
                                                .addOnSuccessListener {
                                                    Log.d("UPDATE", "Success")
                                                }
                                                .addOnFailureListener {
                                                    Log.d("UPDATE", "Error" + it.message)
                                                }
                                        }

                                    }
                                    override fun onCancelled(databaseError: DatabaseError) {
                                        // Getting Data failed, log a message
                                        Log.w("AWD", "LoginData:onCancelled", databaseError.toException())
                                        // ...
                                        Toast.makeText(activity?.applicationContext, "error", Toast.LENGTH_LONG).show()
                                    }
                                }
                            )

                        }
                    })
                ?.addOnFailureListener(OnFailureListener { e ->
                    print(e.message)
                })
        }
    }

    override fun itemClick(position: Int) {
        val selectedAgency = agencyList[position]
        Toast.makeText(requireContext(), selectedAgency.name, Toast.LENGTH_LONG).show()
        val intent = Intent(requireContext(), NecessityActivity::class.java)
        intent.putExtra("agencyName", selectedAgency.name)
        intent.putExtra("agencyId", selectedAgency.id)
        startActivity(intent)
    }

    private fun addNewAgent(newAgent: Agency) {
        database.child(newAgent.id).setValue(newAgent)
            .addOnSuccessListener {
                Log.d("Add Agency", "Done")
            }
            .addOnFailureListener {
                Log.d("Add Agency", "Error" + it.message)
            }
    }

    private fun readAgency(mContext: NecessityFragment) {
        val TAG = "Agency"
        database = FirebaseDatabase.getInstance().getReference("agency")

        database.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                if (dataSnapshot.exists()) {
                    agencyList = arrayListOf<Agency>()
                    for (agency in dataSnapshot.children) {
                        // TODO: handle the post
                        val agent = agency.getValue(Agency::class.java)
                        agencyList.add(agent!!)
                        Log.d(TAG, "added")
                    }

                    val adapter = AgencyAdapter(agencyList, mContext)

                    binding.recyclerViewAgency.adapter = adapter
                }
            }

            override fun onCancelled(databaseError: DatabaseError) {
                // Getting Post failed, log a message
                Log.w(TAG, "loadPost:onCancelled", databaseError.toException())
                // ...
            }
        })
    }
}