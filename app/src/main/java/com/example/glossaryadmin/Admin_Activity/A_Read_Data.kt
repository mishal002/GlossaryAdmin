package com.example.glossaryadmin.Admin_Activity

import Recycleview
import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.glossaryadmin.AHomePage
import com.example.glossaryadmin.All_LogI_in.MainActivity
import com.example.glossaryadmin.Model_Class.CategoryInsert
import com.example.glossaryadmin.R
import com.example.glossaryadmin.databinding.ActivityAreadDataBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class A_Read_Data : AppCompatActivity() {

    lateinit var binding: ActivityAreadDataBinding
    var list = arrayListOf<dbshowhelper>()
    var SCategory = arrayOf<String>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAreadDataBinding.inflate(layoutInflater)
        setContentView(binding.root)


//       Progress bar
        binding.psBar.visibility = View.VISIBLE
        /*Ads*/
        binding.addCategoryBtn.setOnClickListener {
            AddCategory()
        }
        binding.menuBtn.setOnClickListener {
            binding.myDrawerLayout.openDrawer(GravityCompat.START)

        }
        DrawerLayoutClick()
        binding.CreateListBtn.setOnClickListener {
            var intent = Intent(this, AHomePage::class.java)
            startActivity(intent)
        }
        ReadData()
    }

    fun DrawerLayoutClick() {
        binding.navView.setNavigationItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.MyAccount_btn -> {
//                    var intent = Intent(this, MainActivity::class.java)
//                    startActivity(intent)
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
    fun ReadData() {

        var firebaseDatabase = FirebaseDatabase.getInstance()
        var ref = firebaseDatabase.reference


        ref.child("Product").addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                for (x in snapshot.children) {
                    var id = x.child("id").getValue().toString()
                    var name = x.child("name").getValue().toString()
                    var category = x.child("category").getValue().toString()
                    var price = x.child("price").getValue().toString()
                    var des = x.child("description").getValue().toString()
                    var image = x.child("image").getValue().toString()
                    var cid = x.child("cid").getValue().toString()

                    var key = x.key

                    var dbshow = dbshowhelper(id, name, category, price, des, key, image,cid)
                    list.add(dbshow)

                    SCategory += x.child("category").getValue().toString()

                }
                RvSetup(list)
                binding.psBar.visibility = View.GONE

            }

            override fun onCancelled(error: DatabaseError) {

            }
        })
    }

    fun AddCategory() {
        var dialog = Dialog(this)
        dialog.setContentView(R.layout.dilog_add_category)
        dialog.show()

        var Add = dialog.findViewById<Button>(R.id.AddBtn)
        var id = dialog.findViewById<EditText>(R.id.CategoryIdEdtTxt)
        var category = dialog.findViewById<EditText>(R.id.AddCategoryEdt)
        var firebaseDatabase = FirebaseDatabase.getInstance()

        var databaseReference = firebaseDatabase.reference

        databaseReference.child("Category").addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {

                list.clear()
                for (x in snapshot.children) {

                    var category = x.child("id").getValue().toString()

//                    cat = category.toInt()
                }

//                CategoryId.text = (cat!! + 1).toString()

            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })

        Add.setOnClickListener {

            if (id.length() == 0) {

                id.error = "Id"

            } else if (category.length() == 0) {

                category.error = "Name"
            } else {
                var dbCategory = CategoryInsert(
                    id.text.toString(),
                    category.text.toString()
                )

                databaseReference.child("Category").push().setValue(dbCategory)

                dialog.dismiss()
            }
        }
    }


    fun RvSetup(list: ArrayList<dbshowhelper>) {
        var adapter = Recycleview(this@A_Read_Data, list)
        var layoutmanager = LinearLayoutManager(this)
        binding.RvView.layoutManager = layoutmanager
        binding.RvView.adapter = adapter
    }

}

data class dbshowhelper(
    val id: String,
    val name: String,
    val category: String?,
    val price: String,
    val des: String,
    val key: String?,
    val image: String? = null,
    val cid : String

    )

data class DBCategory(
    val id: String,
    val category: String,
)