package com.example.glossaryadmin.User_Activity

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.example.glossaryadmin.All_LogI_in.MainActivity
import com.example.glossaryadmin.R
import com.example.glossaryadmin.UserFragment.Category_Fragment
import com.example.glossaryadmin.UserFragment.Home_Fragment
import com.example.glossaryadmin.UserFragment.offer_Fragment
import com.example.glossaryadmin.UserFragment.profile_Fragment
import com.example.glossaryadmin.databinding.ActivityUhomePageBinding
import com.google.firebase.auth.FirebaseAuth

class UHomePage : AppCompatActivity() {

    lateinit var binding: ActivityUhomePageBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUhomePageBinding.inflate(layoutInflater)
        setContentView(binding.root)
//        load home Fragment
        profilePhoto()

        binding.addCartBtn.setOnClickListener {
            var intent = Intent(this, AddCartActivity::class.java)
            startActivity(intent)
        }

        binding.ProfileImg.setOnClickListener {
            binding.myDrawerLayout.openDrawer(GravityCompat.START)
        }
        loadFragment(Home_Fragment())
        DrawerLayoutClick()

        binding.bottomNav.setOnItemSelectedListener() {
            when (it.itemId) {
                R.id.home ->
                    loadFragment(Home_Fragment())

                R.id.category ->
                    loadFragment(Category_Fragment())

                R.id.offer ->
                    loadFragment(offer_Fragment())

                R.id.profile ->
                    loadFragment(profile_Fragment())
            }
            true
        }
    }

    fun loadFragment(fragment: Fragment) = supportFragmentManager.beginTransaction().apply {
        replace(R.id.flFragment, fragment).commit()
    }


    fun DrawerLayoutClick() {
        binding.navView.setNavigationItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.MyAccount_btn -> {
                    var intent = Intent(this, profile_Fragment::class.java)
                    startActivity(intent)
                    Toast.makeText(this, "My Profile", Toast.LENGTH_SHORT).show()
                    true
                }
                R.id.nav_settings -> {
                    Toast.makeText(this, "People", Toast.LENGTH_SHORT).show()
                    true
                }
                R.id.nav_logout -> {
                    //LOGOUT.........
                    var firebaseAuth = FirebaseAuth.getInstance()
                    firebaseAuth.signOut()
                    var intent = Intent(this, MainActivity::class.java)
                    startActivity(intent)
                    true
                }
                else -> {
                    false
                }
            }
        }
    }

    fun profilePhoto() {
        var firebaseAuth = FirebaseAuth.getInstance()
        var user = firebaseAuth.currentUser
        var uid = user?.photoUrl

        Glide.with(this!!)  //2
            .load(uid) //3
            .centerCrop() //4
            .into(binding.ProfileImg)
    }
}