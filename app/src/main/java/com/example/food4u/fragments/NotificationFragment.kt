package com.example.food4u.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.food4u.R
import com.example.food4u.adapter.EventsAdapter
import com.example.food4u.adapter.NotificationAdapter
import com.example.food4u.databinding.FragmentNotificationBinding
import com.example.food4u.modal.Events
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.*
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_add_new_card.view.*


class NotificationFragment : Fragment() {
    private lateinit var binding: FragmentNotificationBinding
    private lateinit var database: DatabaseReference
    private lateinit var notificationList: ArrayList<Events>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentNotificationBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val mContext = this

        binding.notificationRecycleView.layoutManager = LinearLayoutManager(requireContext())
        binding.notificationRecycleView.setHasFixedSize(true)
        readEvents(mContext)

    }

    private fun readEvents(mContext: NotificationFragment) {
        val TAG = "Events"
        database = FirebaseDatabase.getInstance().getReference("Events")

        database.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                if (dataSnapshot.exists()) {
                    notificationList = arrayListOf<Events>()
                    for (events in dataSnapshot.children) {
                        // TODO: handle the post
                        val event = events.getValue(Events::class.java)
                        notificationList.add(event!!)
                        Log.d(TAG, "added")
                    }

//                    val adapter = NotificationAdapter(notificationList, mContext)
//
//                    binding.notificationRecycleView.adapter = adapter
                }
            }

            override fun onCancelled(databaseError: DatabaseError) {
                // Getting Post failed, log a message
                Log.w(TAG, "loadPost:onCancelled", databaseError.toException())
                // ...
            }
        })
    }

//    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
//        super.onViewCreated(view, savedInstanceState)
//
//        database = FirebaseDatabase.getInstance().reference
//        database.child("Events").get()
//            .addOnSuccessListener { rec->
//                if(rec!=null){
//                    val events = rec.getValue(Events::class.java)
//                    if (events != null){
//
//                    }
//                }
//            }
//
//
//    }
}