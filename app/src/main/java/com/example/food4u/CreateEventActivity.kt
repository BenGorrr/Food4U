package com.example.food4u

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContract
import androidx.activity.result.contract.ActivityResultContracts
import com.example.food4u.databinding.ActivityCreateEventBinding
import com.example.food4u.databinding.ActivityDonateNowBinding
import com.example.food4u.databinding.ActivityFundRaisingInfoBinding
import com.example.food4u.fragments.FundRaisingFragment
import com.example.food4u.modal.Events
import com.example.food4u.modal.Product
import com.google.android.gms.tasks.OnFailureListener
import com.google.android.gms.tasks.OnSuccessListener
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.UploadTask
import java.util.*

class CreateEventActivity : AppCompatActivity() {
    private lateinit var binding: ActivityCreateEventBinding
    private lateinit var database: DatabaseReference
    var imageURL:String=""
    private lateinit var imageUri: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCreateEventBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnUploadEventImage.setOnClickListener{
            startForResult.launch("image/*")
        }

        binding.btnConfirmCreateEvent.setOnClickListener{
            val organizerName = binding.inputOrganizerName.text.toString().trim()
            val organizerAddress = binding.inputOrganizerAddress.text.toString().trim()
            val title = binding.inputEventTitle.text.toString().trim()
            val goal = binding.inputEventGoal.text.toString().trim()
            val description = binding.inputEventDesc.text.toString().trim()
            var successful = true;

            if (organizerName.isEmpty()) {
                binding.inputOrganizerName.error = "This field cannot be empty"
                successful = false
            }
            if (organizerAddress.isEmpty()) {
                binding.inputOrganizerAddress.error = "This field cannot be empty"
                successful = false
            }
            if (goal.isEmpty()) {
                binding.inputEventGoal.error = "This field cannot be empty"
                successful = false
            }
            if (description.isEmpty()) {
                binding.inputEventDesc.error = "This field cannot be empty"
                successful = false
            }

            if(successful){

                database = FirebaseDatabase.getInstance().getReference("Events")
                val key = database.push().key!!

                val fileUri = imageUri
                val filename = UUID.randomUUID().toString() + ".png"
                val refStorage = FirebaseStorage.getInstance().reference.child("images/FundraisingEvent/$filename")

                refStorage.putFile(Uri.parse(fileUri))
                    .addOnSuccessListener (
                        OnSuccessListener<UploadTask.TaskSnapshot> { taskSnapshot ->
                            taskSnapshot.storage.downloadUrl.addOnSuccessListener {
                                imageURL = it.toString()
                                Log.d("GET IMAGE URL", imageURL)
                                val newEvent = Events(key,organizerName,organizerAddress,title,goal.toFloat(),description,imageURL, FirebaseAuth.getInstance().currentUser!!.uid)
                                addNewEvent(newEvent)
                            }
                        })
                    ?.addOnFailureListener(OnFailureListener { e ->
                        print(e.message)
                    })

//                val intentFundraisingInfo: Intent = Intent(this, MainActivity::class.java)
//                startActivity(intentFundraisingInfo)
                finish()
            }
        }
    }

    private fun addNewEvent(newEvent:Events) {
        database.child(newEvent.id).setValue(newEvent)
            .addOnSuccessListener {
                Log.d("Successful","Successful")
                //Displaying Toast after event created successfully
                Toast.makeText(this,"Event Create Successfully",Toast.LENGTH_SHORT).show();
            }
            .addOnFailureListener{
                Log.d("Something went wrong", "Error" + it.message)
            }
    }

    val startForResult = registerForActivityResult(
        ActivityResultContracts.GetContent()){ uri->
        binding.imageDonationEvent.setImageURI(uri)
        imageUri=uri.toString()
        Log.d("GET IMAGE URI", imageUri)
    }

}