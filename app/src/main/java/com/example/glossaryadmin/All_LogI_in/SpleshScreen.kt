package com.example.glossaryadmin.All_LogI_in

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity
import com.example.glossaryadmin.R
import com.google.firebase.auth.FirebaseAuth

class SpleshScreen : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splesh_screen)
//
//        Handler().postDelayed({
//            val intent = Intent(this, MainActivity::class.java)
//            startActivity(intent)
//            finish()
//        }, 3000)

        var firebaseAuth = FirebaseAuth.getInstance()
        var user = firebaseAuth.currentUser

        Handler().postDelayed(Runnable {
            if (user != null) {
                var intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
            } else {
                var intent = Intent(this, UCreateUser::class.java)
                startActivity(intent)
            }
            finish()
        }, 3000)
    }

}
