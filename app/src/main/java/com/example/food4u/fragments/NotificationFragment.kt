package com.example.food4u.fragments

import android.app.AlertDialog
import android.content.DialogInterface
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.food4u.R
import com.example.food4u.adapter.DonorsAdapter
import com.example.food4u.adapter.EventsAdapter
import com.example.food4u.adapter.NotificationAdapter
import com.example.food4u.databinding.FragmentNotificationBinding
import com.example.food4u.modal.Events
import com.example.food4u.modal.Notification
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.*
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_add_new_card.view.*


class NotificationFragment : Fragment(), NotificationAdapter.onItemClickListener {
    private lateinit var binding: FragmentNotificationBinding
    private lateinit var database: DatabaseReference
    private lateinit var notificationList: ArrayList<Notification>
    private lateinit var notificationIdList: ArrayList<String>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentNotificationBinding.inflate(inflater, container, false)

        database = FirebaseDatabase.getInstance().reference

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val mContext = this

        notificationIdList = arrayListOf()
        notificationList = arrayListOf()
        binding.notificationRecycleView.layoutManager = LinearLayoutManager(requireContext())
        binding.notificationRecycleView.setHasFixedSize(true)
        readNoti(mContext)

        binding.btnDeleteNotification.setOnClickListener {
            deleteNoti()
        }
    }

    fun deleteNoti() {
        val builder = AlertDialog.Builder(activity)
        builder.setTitle("Confirm delete?")
        builder.setMessage("Are you sure you want to clear all notifications?")
        builder.setPositiveButton("Yes", DialogInterface.OnClickListener { dialog, id ->
            val userId = FirebaseAuth.getInstance().uid.toString()
            database.child("UserNotification").child(userId).removeValue()
            //readNoti()
            dialog.cancel()
        })
        builder.setNegativeButton("Cancel", DialogInterface.OnClickListener { dialog, id ->
            dialog.cancel()
        })
        val alert: AlertDialog = builder.create()
        alert.show()
    }

    override fun itemClick(position: Int) {
        val noti = notificationList[position]
        val builder = AlertDialog.Builder(activity)
        builder.setTitle(noti.title)
        builder.setMessage(noti.msg)
        builder.setPositiveButton("Done", DialogInterface.OnClickListener { dialog, id ->
            dialog.cancel()
        })
        val alert: AlertDialog = builder.create()
        alert.show()
    }

    private fun readNoti(mContext: NotificationFragment) {
        val TAG = "Events"
        database = FirebaseDatabase.getInstance().reference
        val userId = FirebaseAuth.getInstance().uid.toString()
        database.child("UserNotification/${userId}").addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                if (dataSnapshot.exists()) {
                    for (notiId in dataSnapshot.children) {
                        // TODO: handle the post
                        notificationList = arrayListOf()
                        val notiId = notiId.key.toString()
                        notificationIdList.add(notiId)

                        database.child("Notification/$notiId").get().addOnSuccessListener {
                            val noti = it.getValue(Notification::class.java)!!
                            notificationList.add(noti)

                            val adapter = NotificationAdapter(notificationList, mContext)

                            binding.notificationRecycleView.adapter = adapter

                            if (notificationList.isNotEmpty()) {
                                binding.notificationRecycleView.visibility = View.VISIBLE
                                binding.notificationMessagee.visibility = View.GONE
                                binding.notificationMessage2.visibility = View.GONE
                            }
                            else binding.notificationRecycleView.visibility = View.GONE
                            binding.noOfNotify.text = notificationList.size.toString()
                        }
                        val adapter = NotificationAdapter(notificationList, mContext)
                        binding.notificationRecycleView.adapter = adapter
                    }


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