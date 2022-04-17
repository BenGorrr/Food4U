package com.example.food4u.fragments

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.food4u.*
import com.example.food4u.R
import com.example.food4u.adapter.AgencyAdapter
import com.example.food4u.adapter.CartAdapter
import com.example.food4u.adapter.EventsAdapter
import com.example.food4u.databinding.FragmentFundRaisingBinding
import com.example.food4u.databinding.FragmentNecessityBinding
import com.example.food4u.modal.Agency
import com.example.food4u.modal.Events
import com.google.firebase.database.*


class FundRaisingFragment : Fragment(), EventsAdapter.onItemClickListener {

    private lateinit var binding: FragmentFundRaisingBinding
    private lateinit var database: DatabaseReference
    private lateinit var eventList: ArrayList<Events>
    private var mContext = this

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentFundRaisingBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        database = FirebaseDatabase.getInstance().reference
        // do listener and func here
        binding.btnCreateEvent.setOnClickListener {
            val intentCreateEvent: Intent = Intent(requireContext(), CreateEventActivity::class.java)
            startActivity(intentCreateEvent)
        }

        binding.rvFundraisingEvents.layoutManager = LinearLayoutManager(activity!!.applicationContext)
        binding.rvFundraisingEvents.setHasFixedSize(true)
        readEvents()

    }

    override fun itemClick(position: Int) {
        val event = eventList[position]

        val intent = Intent(requireContext(), DonateNowActivity::class.java)
        intent.putExtra("eventId", event.id)
        startActivity(intent)

    }

    fun readEvents() {
        val TAG = "Events"

        database.child("Events").addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                if (dataSnapshot.exists()) {
                    eventList = arrayListOf<Events>()
                    for (event in dataSnapshot.children) {
                        // TODO: handle the post
                        val event = event.getValue(Events::class.java)
                        eventList.add(event!!)
                        Log.d(TAG, "added")
                    }

                    val adapter = EventsAdapter(eventList, mContext)

                    binding.rvFundraisingEvents.adapter = adapter
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