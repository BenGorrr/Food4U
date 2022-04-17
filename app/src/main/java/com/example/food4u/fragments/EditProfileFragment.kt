package com.example.food4u.fragments

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.food4u.ChangePasswordActivity
import com.example.food4u.databinding.FragmentEditProfileBinding
import com.example.food4u.modal.User
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.fragment_edit_profile.*
import com.example.food4u.ProfilePage
import com.example.food4u.userSignin
import kotlinx.android.synthetic.main.activity_profile_page.*

import androidx.activity.result.contract.ActivityResultContracts
import com.google.android.gms.tasks.OnFailureListener
import com.google.android.gms.tasks.OnSuccessListener
import com.google.firebase.database.*
import com.google.firebase.database.ktx.getValue
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.UploadTask
import com.squareup.picasso.Picasso
import java.util.*


class EditProfileFragment : Fragment() {
    private lateinit var binding: FragmentEditProfileBinding
    private lateinit var database: DatabaseReference
    private var selectedUri: Uri? = null
    private lateinit var ref:StorageReference

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentEditProfileBinding.inflate(inflater, container, false)
        ref = FirebaseStorage.getInstance().reference
        selectedUri = Uri.parse("")

        val editName = binding.tfEditName
        val editBd = binding.tfEditBirthDate
        val editAddress = binding.tfEditAddress
        val editPhoneNo = binding.tfPhoneNo
        val btnChangePw = binding.btnChangePw
        val btnSave = binding.btnSave
        val editProfPicIcon = activity!!.profPic

        val DB = Firebase.database("https://food4u-9d1c8-default-rtdb.asia-southeast1.firebasedatabase.app/")
        database= DB.getReference("userDB")
        database.child("User").child(Firebase.auth.uid.toString()).get()
            .addOnSuccessListener { rec->
                if(rec!=null){
                    editName.setHint(rec.child("name").value.toString())
                    editBd.setHint(rec.child("birthDate").value.toString())
                    editAddress.setHint(rec.child("address").value.toString())
                    editPhoneNo.setHint(rec.child("phoneNo").value.toString())
                }
            }



        editProfPicIcon.setOnClickListener {
            Log.i("check","yesla")
            startForResult.launch("image/*")
        }

        btnSave.setOnClickListener{
            val dateFromEditText = editBd.text.toString()
            val phoneNo = editPhoneNo.text.toString()
            if((dateFromEditText!="" && !(dateFromEditText.matches(getPattern().toRegex()))) || (phoneNo!="" && !(phoneNo.length in 10..11))) {
                if(dateFromEditText!="" && !(dateFromEditText.matches(getPattern().toRegex()))){
                    binding.tfEditBirthDate.setError("Your date is incorrect!")
                }
                else binding.tfPhoneNo.setError("Invalid phone number!!")
            }
            else{
                activity!!.tvMyProfile.visibility = View.VISIBLE
                activity!!.tvEditProfile.visibility = View.INVISIBLE
                activity!!.tvUserDisplayName.visibility = View.VISIBLE
                activity!!.tvNumOfDonation.visibility = View.VISIBLE
                activity!!.btnManagePayment.visibility = View.VISIBLE
                activity!!.editIcon.visibility = View.VISIBLE
                database.child("User").child(Firebase.auth.uid.toString()).get()
                    .addOnSuccessListener { rec->
                        if(rec!=null){
                            if(editName.text.toString()!=""){
                                database.child("User").child(Firebase.auth.uid.toString()).child("name").setValue(editName.text.toString())
                            }else database.child("User").child(Firebase.auth.uid.toString()).child("name").setValue(rec.child("name").value.toString())
                            if(dateFromEditText!=""){
                                database.child("User").child(Firebase.auth.uid.toString()).child("birthDate").setValue(dateFromEditText)
                            }
                            if(editAddress.text.toString()!=""){ //if there is edited entry
                                database.child("User").child(Firebase.auth.uid.toString()).child("address").setValue(editAddress.text.toString())
                            }else database.child("User").child(Firebase.auth.uid.toString()).child("address").setValue(rec.child("address").value.toString()) //remain same value
                            if(editPhoneNo.text.toString()!=""){ //if there is edited entry
                                database.child("User").child(Firebase.auth.uid.toString()).child("phoneNo").setValue(editPhoneNo.text.toString())
                            }else database.child("User").child(Firebase.auth.uid.toString()).child("phoneNo").setValue(rec.child("phoneNo").value.toString()) //remain same value
                        }
                    }
                val selectedUri = selectedUri
                if(selectedUri!=null) uploadToFirebase(selectedUri)
                //replaceFragment(ProfileFragment())
                val intent = Intent(activity, ProfilePage::class.java)
                intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION)
                startActivity(intent)
                activity!!.finish()
            }

//            activity!!.supportFragmentManager.beginTransaction().detach(ProfileFragment()).attach(ProfileFragment()).commit()
//            replaceFragment(ProfileFragment())
        }

        btnChangePw.setOnClickListener{
            val intent = Intent(activity, ChangePasswordActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION)
            startActivity(intent)
            activity!!.finish()
        }
        return binding.root
    }

    val startForResult = registerForActivityResult(
        ActivityResultContracts.GetContent()){ uri->
            if(uri!=null){
                selectedUri = uri
                activity!!.profPic.setImageURI(selectedUri)
            }
    }

    fun uploadToFirebase(fileUri:Uri){
        if(fileUri.toString().isNotEmpty()){
            val filename = UUID.randomUUID().toString() + ".png"
            val imgRef = ref.child("images/user/" + filename)
            imgRef.putFile(fileUri)
                .addOnSuccessListener (
                    OnSuccessListener<UploadTask.TaskSnapshot> { taskSnapshot ->
                        taskSnapshot.storage.downloadUrl.addOnSuccessListener {
                            val imageUrl = it.toString()
                            database.child("User").child(Firebase.auth.uid.toString()).child("imgUrl").setValue(imageUrl)
                        }
                    })
                ?.addOnFailureListener(OnFailureListener { e ->
                    print(e.message)
                })
        }
    }
    fun getPattern() = "^((?:(?:1[6-9]|2[0-9])\\d{2})(-)(?:(?:(?:0[13578]|1[02])(-)31)|((0[1,3-9]|1[0-2])(-)(29|30))))\$|^(?:(?:(?:(?:1[6-9]|[2-9]\\d)?(?:0[48]|[2468][048]|[13579][26])|(?:(?:16|[2468][048]|[3579][26])00)))(-)02(-)29)\$|^(?:(?:1[6-9]|2[0-9])\\d{2})(-)(?:(?:0[1-9])|(?:1[0-2]))(-)(?:0[1-9]|1\\d|2[0-8])\$"


    //change fragment
//    private fun replaceFragment(fragment1: Fragment) {
//        val fragmentManager = activity!!.supportFragmentManager
//        val fragmentTransaction = fragmentManager.beginTransaction()
//        fragmentTransaction.replace(R.id.container, fragment1)
//        fragmentTransaction.commit()
//    }

    //    public fun passValues(): User {
//        val editName = binding.tfEditName
//        val editBd = binding.tfEditBirthDate
//        val editAddress = binding.tfEditAddress
//        val editPhoneNo = binding.tfPhoneNo
//        val dateFromEditText = editBd.text.toString()
//        val simpleDateFormat = SimpleDateFormat("dd.MM.yyyy")
//        val dateTime = simpleDateFormat.parse(dateFromEditText)
//        newUser = User("", editName.text.toString(), dateTime, editAddress.text.toString(), editPhoneNo.text.toString(), 0)
//        return newUser
//    }
}

