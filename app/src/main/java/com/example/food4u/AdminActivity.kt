package com.example.food4u

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import com.example.food4u.databinding.ActivityAdminBinding
import com.example.food4u.firebase.firebaseHelper
import com.example.food4u.modal.Agency
import com.example.food4u.modal.Product
import com.google.android.gms.tasks.OnFailureListener
import com.google.android.gms.tasks.OnSuccessListener
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.UploadTask
import kotlinx.android.synthetic.main.activity_admin.*
import java.util.*

class AdminActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAdminBinding
    private lateinit var FB: firebaseHelper
    private lateinit var database: DatabaseReference
    private lateinit var databaseProd: DatabaseReference
    private lateinit var databaseAgency: DatabaseReference
    private var selectedUri: Uri? = null
    private var selectedUriAgency: Uri? = null
    private lateinit var ref: StorageReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAdminBinding.inflate(layoutInflater)
        ref = FirebaseStorage.getInstance().reference
        setContentView(binding.root)

        val adminName = binding.tvName
        val prodName = binding.tfProdName
        val prodPrice = binding.tfProdPrice
        val addProdImgBtn = binding.btnAddImgProd
        val confirmProdBtn = btnConfirmAddProd

        val agencyName = binding.tfAgencyName
        val agencyDesc = binding.agencyDesc
        val addAgencyImgBtn = binding.btnAddAgencyImg
        val confirmAgencyBtn = binding.btnConfirmAddAgency
        val signOutBtn = binding.btnSignMeOut

        FB = firebaseHelper(this)
        val DB = Firebase.database("https://food4u-9d1c8-default-rtdb.asia-southeast1.firebasedatabase.app/")
        database = DB.getReference("userDB")
        databaseProd = DB.getReference("Products")
        databaseAgency = DB.getReference("agency")

        database.child("User").child(Firebase.auth.uid.toString()).get()
            .addOnSuccessListener { rec ->
                if (rec != null) {
                    adminName.text = "Admin name: " + rec.child("name").value.toString()
                }
            }

        //add product
        addProdImgBtn.setOnClickListener {
            startForResult.launch("image/*")
        }
        confirmProdBtn.setOnClickListener{
            if(prodName.text.toString()!="" && prodPrice.text.toString()!=""){
                val price = prodPrice.text.toString().toFloat()
                val newProduct = Product(databaseProd.push().key!!,prodName.text.toString(),price)
                val selectedUri = selectedUri
                if(selectedUri!=null) uploadToFirebase(newProduct ,selectedUri)
            }
            else{
                if(prodName.text.toString()=="") prodName.setError("Please enter a name for your product!")
                if(prodPrice.text.toString()=="") prodPrice.setError("Please enter a price for your product!")
            }
        }

        //add agency
        addAgencyImgBtn.setOnClickListener {
            startForAgencyResult.launch("image/*")
        }
        confirmAgencyBtn.setOnClickListener{
            if(agencyName.text.toString()!="" && agencyDesc.text.toString()!=""){
                val newAgency = Agency(databaseAgency.push().key!!,agencyName.text.toString(),agencyDesc.text.toString())
                val selectedUri = selectedUriAgency
                if(selectedUri!=null) uploadAgencyToFirebase(newAgency ,selectedUri)
            }
            else{
                if(agencyName.text.toString()=="") agencyName.setError("Please enter a name for the agency!")
                if(agencyDesc.text.toString()=="") agencyDesc.setError("Please enter a description for this agency!")
            }
        }

        signOutBtn.setOnClickListener{
            val intent = Intent(this, userSignin::class.java)
            startActivity(intent)
            intent.putExtra("finish", true)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP // To clean up all activities
            startActivity(intent)
            FB.signOut()
            finish()
        }

    }

    private val startForResult = registerForActivityResult(
        ActivityResultContracts.GetContent()){ uri->
        if(uri!=null){
            selectedUri = uri
            this.imgProd.setImageURI(selectedUri)
        }
    }

    private val startForAgencyResult = registerForActivityResult(
        ActivityResultContracts.GetContent()){ uri->
        if(uri!=null){
            selectedUriAgency = uri
            this.imgAgency.setImageURI(selectedUriAgency)
        }
    }

    fun uploadToFirebase(product:Product, fileUri:Uri){
        if(fileUri.toString().isNotEmpty()){
            val filename = UUID.randomUUID().toString() + ".png"
            val imgRef = ref.child("images/products/" + filename)
            imgRef.putFile(fileUri)
                .addOnSuccessListener (
                    OnSuccessListener<UploadTask.TaskSnapshot> { taskSnapshot ->
                        taskSnapshot.storage.downloadUrl.addOnSuccessListener {
                            val imageUrl = it.toString()
                            product.imageURL = imageUrl
                            databaseProd.child(product.id).setValue(product).addOnSuccessListener {
                                Toast.makeText(applicationContext, "Product added!",Toast.LENGTH_SHORT).show()
                                //gnn sir cannot see toast la
                                val intent = Intent(this, AdminActivity::class.java)
                                intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION)
                                startActivity(intent)
                                finish()
                            }
                        }
                    })
                ?.addOnFailureListener(OnFailureListener { e ->
                    print(e.message)
                })
        }
    }

    fun uploadAgencyToFirebase(agency: Agency, fileUri:Uri){
        if(fileUri.toString().isNotEmpty()){
            val filename = UUID.randomUUID().toString() + ".png"
            val imgRef = ref.child("images/agency/" + filename)
            imgRef.putFile(fileUri)
                .addOnSuccessListener (
                    OnSuccessListener<UploadTask.TaskSnapshot> { taskSnapshot ->
                        taskSnapshot.storage.downloadUrl.addOnSuccessListener {
                            val imageUrl = it.toString()
                            agency.imageURL = imageUrl
                            databaseAgency.child(agency.id).setValue(agency).addOnSuccessListener {
                                Toast.makeText(applicationContext, "Agency added!",Toast.LENGTH_SHORT).show()
                                //gnn sir cannot see toast la
                                val intent = Intent(this, AdminActivity::class.java)
                                intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION)
                                startActivity(intent)
                                finish()
                            }
                        }
                    })
                ?.addOnFailureListener(OnFailureListener { e ->
                    print(e.message)
                })
        }
    }
}