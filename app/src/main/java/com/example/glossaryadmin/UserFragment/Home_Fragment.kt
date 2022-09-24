package com.example.glossaryadmin.UserFragment


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.glossaryadmin.AHomePage.Companion.SCategory
import com.example.glossaryadmin.Adapter.UReadAdapter
import com.example.glossaryadmin.Admin_Activity.dbshowhelper
import com.example.glossaryadmin.databinding.FragmentHomeBinding
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener


class Home_Fragment : Fragment() {

    lateinit var binding: FragmentHomeBinding
    var list = arrayListOf<dbshowhelper>()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        binding = FragmentHomeBinding.inflate(layoutInflater)
        ReadData()

        /*return*/
        return binding.root
    }


    fun ReadData() {

        var firebaseDatabase = FirebaseDatabase.getInstance()
        var ref = firebaseDatabase.reference

        ref.child("Product").addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {

                list.clear()
                for (x in snapshot.children) {
                    var id = x.child("id").getValue().toString()
                    var name = x.child("name").getValue().toString()
                    var category = x.child("category").getValue().toString()
                    var price = x.child("price").getValue().toString()
                    var des = x.child("description").getValue().toString()
                    var image = x.child("image").getValue().toString()

                    var key = x.key

                    var dbshow = dbshowhelper(id, name, category, price, des, key, image)
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
    fun RvSetup(list: ArrayList<dbshowhelper>) {
        var adapter = UReadAdapter(this, list)
        var FragmentManager = LinearLayoutManager(activity)
        binding.UserRvView.layoutManager = FragmentManager
        binding.UserRvView.adapter = adapter
    }
}