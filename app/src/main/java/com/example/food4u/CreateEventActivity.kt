package com.example.food4u

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContract
import androidx.activity.result.contract.ActivityResultContracts
import com.example.food4u.databinding.ActivityCreateEventBinding
import com.example.food4u.databinding.ActivityDonateNowBinding
import com.example.food4u.databinding.ActivityFundRaisingInfoBinding
import com.example.food4u.fragments.FundRaisingFragment

class CreateEventActivity : AppCompatActivity() {
    lateinit var binding: ActivityCreateEventBinding

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

            if(successful == true){
                //Displaying Toast after event created successfully
                Toast.makeText(getApplicationContext(),"Event Create Successfully",Toast.LENGTH_SHORT).show();

                val intentFundraisingInfo: Intent = Intent(this, MainActivity::class.java)
                startActivity(intentFundraisingInfo)
            }

        }
    }

    val startForResult = registerForActivityResult(
        ActivityResultContracts.GetContent()){ uri->
        binding.imageDonationEvent.setImageURI(uri)
    }
}