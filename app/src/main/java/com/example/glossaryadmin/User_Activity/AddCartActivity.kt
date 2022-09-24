package com.example.glossaryadmin.User_Activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.glossaryadmin.Adapter.CartDataAdapter
import com.example.glossaryadmin.Model_Class.DBTemp
import com.example.glossaryadmin.databinding.ActivityAddCartBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import java.util.ArrayList

class AddCartActivity : AppCompatActivity() {

    val cartList = arrayListOf<DBTemp>()
//    var total : Int = 0
    lateinit var binding: ActivityAddCartBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityAddCartBinding.inflate(layoutInflater)
        setContentView(binding.root)
        readProductData()

        binding.backArrow.setOnClickListener {
            onBackPressed()
        }

    }
     private fun readProductData() {

        var firebaseDatabase = FirebaseDatabase.getInstance()
        var databaseReference = firebaseDatabase.reference

        var firebaseAuth = FirebaseAuth.getInstance()
        var user = firebaseAuth.currentUser
        var uid = user?.uid

        databaseReference.child("Cart").child(uid.toString()).addValueEventListener(object :
            ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {

                cartList.clear()
//                total=0;

                for (x in snapshot.children) {


                    var pname = x.child("pname").getValue().toString()
                    var pprice = x.child("pprice").getValue().toString()
                    var pdes = x.child("pdes").getValue().toString()
                    var pcat = x.child("pcat").getValue().toString()
                    var pimage = x.child("pimage").getValue().toString()
                    var key = x.key.toString()

                    var DBTemp = DBTemp(pname, pprice, pdes, pcat, pimage,key)


//                    total = (pprice!!.toInt()*qua.toInt()) + total
//
                    cartList.add(DBTemp)
                }

//                Toast.makeText(this@cartActivity, "$total", Toast.LENGTH_SHORT).show()


                setProductData(cartList)
            }

            override fun onCancelled(error: DatabaseError) {

            }
        })

    }

    private fun setProductData(cartList: ArrayList<DBTemp>) {
        var adapter  = CartDataAdapter(this,cartList)
        var layoutmanager = LinearLayoutManager(this)
        binding.rvAdd.layoutManager = layoutmanager
        binding.rvAdd.adapter = adapter


    }
}