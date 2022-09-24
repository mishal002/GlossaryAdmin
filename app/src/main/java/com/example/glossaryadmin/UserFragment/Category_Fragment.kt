package com.example.glossaryadmin.UserFragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.glossaryadmin.Adapter.AdapterCategory
import com.example.glossaryadmin.Admin_Activity.dbshowhelper
import com.example.glossaryadmin.databinding.FragmentCategoryBinding
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class Category_Fragment : Fragment() {
    var list = arrayListOf<dbshowhelper>()
    lateinit var binding: FragmentCategoryBinding
    var no = arrayOf(
        1, 2, 3, 4, 5, 6,7,8)


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        binding = FragmentCategoryBinding.inflate(layoutInflater)
        binding.psBar.visibility = View.VISIBLE

        binding.electronics.setOnClickListener{
            ReadData(1)
        }


        /*return*/
        return binding.root
    }

    fun ReadData(i : Int) {

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
                    var cid = x.child("cid").getValue().toString()
                    var image = x.child("image").getValue().toString()
                    var uid = x.child("uid").getValue().toString()

                    var key = x.key

                    var dbshow = dbshowhelper(id, name, category, price, des, key, image,cid)
                    list.add(dbshow)

                    if (i == cid.toInt() ){

                        var productData = dbshowhelper(id, name, price, des, category, image,key, cid)

                        list.add(productData)

                    }

                }
                RvSetup(list)
                binding.psBar.visibility = View.GONE

            }

            override fun onCancelled(error: DatabaseError) {

            }
        })
    }

    fun RvSetup(list: ArrayList<dbshowhelper>) {
        var adapter = AdapterCategory(this, list)
        var lm = LinearLayoutManager(this.context)
        binding.RvView.layoutManager = lm
        binding.RvView.adapter = adapter
    }


}