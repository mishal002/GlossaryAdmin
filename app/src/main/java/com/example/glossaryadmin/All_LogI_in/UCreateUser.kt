package com.example.glossaryadmin.All_LogI_in

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.InputType
import android.util.Log
import android.widget.CompoundButton
import android.widget.Toast
import com.example.glossaryadmin.databinding.ActivityUcreateUserBinding
import com.google.firebase.auth.FirebaseAuth

class UCreateUser : AppCompatActivity() {

    lateinit var binding : ActivityUcreateUserBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityUcreateUserBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.sSubmitBtn.setOnClickListener {
            singUp(binding.sEmailEdt.text.toString(),binding.sPasswordEdt.text.toString())
        }

        binding.checkbox.setOnCheckedChangeListener(CompoundButton.OnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked) {
                binding.sPasswordEdt.setInputType(InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD)
            } else {
                binding.sPasswordEdt.setInputType(InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD)
            }
        })
    }
    fun singUp(email: String, password: String) {
        var firebaseAuth = FirebaseAuth.getInstance()
        firebaseAuth.createUserWithEmailAndPassword(email, password).addOnSuccessListener { res ->
            Toast.makeText(this, "Success", Toast.LENGTH_SHORT).show()
            var intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
            .addOnFailureListener { error -> Log.e("TAG", "singUp: ${(error.message)}") }
    }
}