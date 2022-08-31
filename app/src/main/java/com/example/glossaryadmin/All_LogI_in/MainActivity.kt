package com.example.glossaryadmin.All_LogI_in

import android.content.Intent
import android.text.SpannableString ;
import android.text.style.UnderlineSpan ;import android.os.Bundle
import android.text.InputType
import android.util.Log
import android.widget.CompoundButton
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.glossaryadmin.Admin_Activity.ALoginPage
import com.example.glossaryadmin.R
import com.example.glossaryadmin.User_Activity.UHomePage
import com.example.glossaryadmin.databinding.ActivityMainBinding
import com.google.android.gms.auth.api.Auth
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.GoogleApiClient
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider

class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding
    private var RC_SING_IN = 2


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //click
        initClick()
    }

    fun initClick() {
        binding.uCreateBtn.setOnClickListener {
            var intent = Intent(this, UCreateUser()::class.java)
            startActivity(intent)
        }

        binding.adminBtn.setOnClickListener {
            var intent = Intent(this, ALoginPage()::class.java)
            startActivity(intent)
        }

        binding.uSubmitBtn.setOnClickListener {
            singUp(binding.EDTEmail.toString(), binding.EDTPassword.toString())
        }
        binding.googleBtn.setOnClickListener {
            googleLogin()
        }

        binding.checkbox.setOnCheckedChangeListener(CompoundButton.OnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked) {
                binding.EDTPassword.setInputType(InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD)
            } else {
                binding.EDTPassword.setInputType(InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD)
            }
        })


    }

    private fun singUp(email: String, password: String) {

        var firebaseAuth = FirebaseAuth.getInstance()
        firebaseAuth.signInWithEmailAndPassword(email, password).addOnSuccessListener { res ->
            Toast.makeText(this, "Success", Toast.LENGTH_SHORT).show()
            binding.uSubmitBtn.setOnClickListener {
                var intent = Intent(this, UHomePage()::class.java)
                startActivity(intent)
            }

        }.addOnFailureListener { error ->
            Toast.makeText(this, "${error.message}", Toast.LENGTH_SHORT).show()
            Log.e("TAG", "singUp: ${error.message}")

        }
    }
    //    Google login

    //    Open Dailog Google
    fun googleLogin() {
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString((R.string.client)))
            .requestEmail()
            .build()
        var googleApiClient = GoogleApiClient.Builder(this)
            .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
            .build()

        val intent = Auth.GoogleSignInApi.getSignInIntent(googleApiClient)
        startActivityForResult(intent, RC_SING_IN)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when (requestCode) {
            RC_SING_IN -> {
                val result = Auth.GoogleSignInApi.getSignInResultFromIntent(data!!)
                var account = result?.signInAccount
                loginwithCredantail(account?.idToken.toString())
            }
        }
    }

    private fun loginwithCredantail(idToken: String) {
        val crd = GoogleAuthProvider.getCredential(idToken, null)
        var firebaseAuth = FirebaseAuth.getInstance()
        firebaseAuth.signInWithCredential(crd).addOnSuccessListener { res ->
            var i = Intent(this, UHomePage::class.java)
            startActivity(i)

        }.addOnFailureListener { error ->
            Log.e("TAG", "loginwithCredantail: ${error.message}")
        }

    }
}