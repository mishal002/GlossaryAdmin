package com.example.glossaryadmin.Adapter

import android.widget.Toast
import com.example.glossaryadmin.Model_Class.DBTemp
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.glossaryadmin.Admin_Activity.dbshowhelper
import com.example.glossaryadmin.R
import com.example.glossaryadmin.UserFragment.Home_Fragment
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase

class UReadAdapter(val activity: Home_Fragment, val list: ArrayList<dbshowhelper>) :
    RecyclerView.Adapter<UReadAdapter.ViewHolder>() {


    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var id = itemView.findViewById<TextView>(R.id.product_id_txt)
        var name = itemView.findViewById<TextView>(R.id.product_name_txt)
        var category = itemView.findViewById<TextView>(R.id.category_txt)
        var price = itemView.findViewById<TextView>(R.id.price_txt)
        var des = itemView.findViewById<TextView>(R.id.des_txt)
        var image = itemView.findViewById<ImageView>(R.id.Image_rv)
        var cart_btn = itemView.findViewById<ImageView>(R.id.cart_btn)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        var View =
            LayoutInflater.from(parent.context).inflate(R.layout.u_read_itemfiile, parent, false)
        return ViewHolder(View)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.id.text = list[position].id
        holder.name.text = list[position].name
        holder.category.text = list[position].category
        holder.price.text = list[position].price
        holder.des.text = list[position].des

// photo
        Glide.with(activity)  //2
            .load(list[position]?.image) //3
            .centerCrop() //4
            .into(holder.image) //8

        holder.cart_btn.setOnClickListener {
            add(position)
            Toast.makeText(activity.activity, "Add item  :- "+list.get(position).name, Toast.LENGTH_SHORT).show()
        }


    }
    private fun add(position: Int) {

        var firebaseDatabase = FirebaseDatabase.getInstance()
        var databaseReference = firebaseDatabase.reference

        var firebaseAuth = FirebaseAuth.getInstance()
        var user = firebaseAuth.currentUser
        var uid = user?.uid

        var cartData = DBTemp(
            list[position].name,
            list[position].price,
            list[position].des!!,
            list[position].category!!,
            list[position].image!!,
            list[position].key!!

        )

        databaseReference.child("Cart").child(uid.toString()).push().setValue(cartData)
    }

    override fun getItemCount(): Int {
        return list.size
    }
}