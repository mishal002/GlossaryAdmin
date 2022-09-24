package com.example.glossaryadmin

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.glossaryadmin.Admin_Activity.A_Read_Data
import com.example.glossaryadmin.Model_Class.CategoryInsert
import com.example.glossaryadmin.databinding.ActivityAhomePageBinding
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import java.io.File

class AHomePage : AppCompatActivity() {

    lateinit var category: String
    var cid: Int? = null
    var id: String? = null
    var l1 = arrayListOf<CategoryInsert>()


    companion object {
        var SCategory = arrayOf<String>("Selcet")
    }

    lateinit var uri: Uri
    lateinit var binding: ActivityAhomePageBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAhomePageBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ReadCategory()

        binding.insertSubmitBtn.setOnClickListener {
            storge()
            UploadIamage()
            var intent = Intent(this, A_Read_Data()::class.java)
            startActivity(intent)
        }
        binding.insertImg.setOnClickListener {
            var intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
            startActivityForResult(intent, 100)
        }
    }

    fun storge() {
        var firebaseDatabase = FirebaseDatabase.getInstance()
        var reference = firebaseDatabase.reference
        var m1 = dbhelper(
            binding.AInsertIdEdt.text.toString(),
            binding.AInsertNameEdt.text.toString(),
            binding.AInsertPriceEdt.text.toString(),
            category.toString(),
            binding.AInsertDesEdt.text.toString(),
            uri.toString(),
            cid.toString()
        )
        reference.child("Product").push().setValue(m1)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (100 == requestCode) {
            uri = data?.data!!
            binding.insertImg.setImageURI(uri)
        }
    }

    fun UploadIamage() {
        var file = File(uri.toString())
        var storage = Firebase.storage
        var storageRef = storage.reference.child("${file.name}")
        var uploadTask = storageRef.putFile(uri)

        uploadTask.addOnSuccessListener { snapsopt ->
            Toast.makeText(this, "Success", Toast.LENGTH_SHORT).show()

        }.addOnFailureListener { error ->
            Toast.makeText(this, "${error.message}", Toast.LENGTH_SHORT).show()
        }
    }

    fun ReadCategory() {
        l1.clear()
        var firebaseDatabase = FirebaseDatabase.getInstance()
        var ref = firebaseDatabase.reference


        ref.child("Category").addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {

                l1.clear()
                for (x in snapshot.children) {
                    var id = x.child("id").getValue().toString()
                    var category = x.child("category").getValue().toString()

                    var key = x.key

                    var dbshow = CategoryInsert(id, category)
                    l1.add(dbshow)

                    SCategory += x.child("category").getValue().toString()

                }
                setupSpinner(SCategory)

            }

            override fun onCancelled(error: DatabaseError) {

            }
        })
    }

    fun setupSpinner(data: Array<String>) {

        val arrayAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, data)
        binding.spinner.adapter = arrayAdapter
        arrayAdapter.notifyDataSetChanged()


        binding.spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(p0: AdapterView<*>?, view: View?, postion: Int, p3: Long) {
                category = data[postion]
                cid = postion + 1
                Toast.makeText(this@AHomePage, "${data[postion]}", Toast.LENGTH_SHORT).show()
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {
                TODO("Not yet implemented")
            }

        }

    }
}

data class dbhelper(
    val Id: String,
    val Name: String,
    val Price: String,
    val Category: String,
    val Description: String,
    val Image: String,
    val cid: String,
)
