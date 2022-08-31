package com.example.glossaryadmin.Admin_Activity

import android.content.Intent
import android.os.Bundle
import android.text.InputType
import android.util.Log
import android.widget.CompoundButton
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.glossaryadmin.databinding.ActivityAloginPageBinding
import com.google.firebase.auth.FirebaseAuth


class ALoginPage : AppCompatActivity() {

    lateinit var binding: ActivityAloginPageBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAloginPageBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.ASubmitBtn.setOnClickListener {
            singUp(binding.EDTEmail.text.toString(), binding.EDTPassword.text.toString())
        }
        binding.checkbox.setOnCheckedChangeListener(CompoundButton.OnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked) {
                binding.EDTPassword.setInputType(InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD)
            } else {
                binding.EDTPassword.setInputType(InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD)
            }
        })

        binding.BackUserBtn.setOnClickListener {
            onBackPressed()
        }

    }

    private fun singUp(email: String, password: String) {

        var firebaseAuth = FirebaseAuth.getInstance()
        firebaseAuth.signInWithEmailAndPassword(email, password).addOnSuccessListener { res ->
            Toast.makeText(this, "Success", Toast.LENGTH_SHORT).show()
            var intent = Intent(this, A_Read_Data()::class.java)
            startActivity(intent)


        }.addOnFailureListener { error ->
            Toast.makeText(this, "${error.message}", Toast.LENGTH_SHORT).show()
            Log.e("TAG", "singUp: ${error.message}")

        }
    }

}