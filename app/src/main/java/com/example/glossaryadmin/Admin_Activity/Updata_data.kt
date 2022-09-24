package com.example.glossaryadmin.Admin_Activity

import android.R
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.example.glossaryadmin.AHomePage.Companion.SCategory
import com.example.glossaryadmin.databinding.ActivityUpdataDataBinding
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import java.io.File


class Updata_data : AppCompatActivity() {

    private var key: String? = null
    private var img: String? = null
    var cid : Int? = null
    lateinit var category: String
    lateinit var binding: ActivityUpdataDataBinding
    var image2: Uri? = null



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUpdataDataBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupSpinner(SCategory)

        getData()

    }

    private fun getData() {

        val id = intent.getIntExtra("n1", 0)
        val name = intent.getStringExtra("n2")
        val price = intent.getStringExtra("n4")
        val des = intent.getStringExtra("n5")
         img = intent.getStringExtra("n7")
         key = intent.getStringExtra("n6")

        binding.AUpdateIdEdt.setText("$id")
        binding.AUpdateNameEdt.setText(name)
        binding.AUpdatePriceEdt.setText(price)
        binding.AUpdateDesEdt.setText(des)
        Glide.with(this).load(img).into(binding.UpdateImg)


        updateImage()

        binding.updateSubmitBtn.setOnClickListener {

            if(image2==null)
            {
                updateData(img.toString())
            }else
            {
                updateImageToStorage()
            }



            var intent = Intent(this, A_Read_Data::class.java)
            startActivity(intent)
        }
    }
    fun setupSpinner(data: Array<String>) {

        val arrayAdapter = ArrayAdapter(this, R.layout.simple_spinner_item, data)
        binding.spinner.adapter = arrayAdapter
        arrayAdapter.notifyDataSetChanged()


        binding.spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(p0: AdapterView<*>?, view: View?, postion: Int, p3: Long) {
                category = data[postion]
                cid = postion + 1
                Toast.makeText(this@Updata_data, "${data[postion]}", Toast.LENGTH_SHORT).show()
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {

            }

        }

    }

    private fun updateImage() {

        binding.UpdateImg.setOnClickListener {

            val intent = Intent(
                Intent.ACTION_PICK,
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI
            )
            startActivityForResult(intent, 1)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == 1) {

            image2 = data?.data!!
            binding.UpdateImg.setImageURI(image2)
        }
    }

    private fun updateImageToStorage() {


        val file = File(image2.toString())
        val storage = Firebase.storage
        val storageReference = storage.reference.child("${file.name}")
        val upload = storageReference.putFile(image2!!)
        upload.addOnSuccessListener { snapshot ->
            snapshot.storage.downloadUrl.addOnSuccessListener { result ->
                val temp = result
                updateData(temp.toString())
            }
        }.addOnFailureListener { error ->
        }

    }

    private fun updateData(uri: String) {

        val firebaseDatabase = FirebaseDatabase.getInstance()
        val databaseReference = firebaseDatabase.reference


        val productData = dbshowhelper(
            binding.AUpdateIdEdt.text.toString(),
            binding.AUpdateNameEdt.text.toString(),
            category,
            binding.AUpdatePriceEdt.text.toString(),
            binding.AUpdateDesEdt.text.toString(),
            uri,
            cid.toString()
        )
        databaseReference.child("Product").child(key!!).setValue(productData)

    }
}