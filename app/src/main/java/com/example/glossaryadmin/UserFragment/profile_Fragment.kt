package com.example.glossaryadmin.UserFragment

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.example.glossaryadmin.All_LogI_in.MainActivity
import com.example.glossaryadmin.databinding.FragmentProfileBinding
import com.google.firebase.auth.FirebaseAuth

class profile_Fragment : Fragment() {

    lateinit var binding: FragmentProfileBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        binding = FragmentProfileBinding.inflate(layoutInflater)
        firebaseprodile()

/*return*/
        return binding.root
    }

    @SuppressLint("UseRequireInsteadOfGet")
    fun firebaseprodile() {
        var firebaseAuth = FirebaseAuth.getInstance()
        var user = firebaseAuth.currentUser
        var uid = user?.photoUrl
        var name = user?.displayName
        var email = user?.email

        Glide.with(activity!!)  //2
            .load(uid) //3
            .centerCrop() //4
            .into(binding.ProfileImg)

        binding.profileName.setText(name)
        binding.profileEmail.setText(email)

        binding.logout.setOnClickListener {
            var firebaseAuth = FirebaseAuth.getInstance()
            firebaseAuth.signOut()
            var intent = Intent(activity, MainActivity::class.java)
            startActivity(intent)
            true
        }
    }
}