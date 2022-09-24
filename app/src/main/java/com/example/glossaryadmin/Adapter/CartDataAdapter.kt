package com.example.glossaryadmin.Adapter

import Recycleview
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.glossaryadmin.Model_Class.DBTemp
import com.example.glossaryadmin.R
import com.example.glossaryadmin.User_Activity.AddCartActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase

class CartDataAdapter(val activity: AddCartActivity, val list: ArrayList<DBTemp>) :
    RecyclerView.Adapter<CartDataAdapter.ViewDate>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewDate {
        var view =
            LayoutInflater.from(parent.context).inflate(R.layout.cart_itemfile, parent, false)
        return ViewDate(view)
    }

    override fun onBindViewHolder(holder: ViewDate, position: Int) {
        holder.mname.text = list[position].pname
        holder.mcat.text = list[position].pcat
        holder.mprice.text = list[position].pprice
        holder.mdes.text = list[position].pdes

        var firebaseDatabase = FirebaseDatabase.getInstance()
        var ref = firebaseDatabase.reference

        var firebaseAuth = FirebaseAuth.getInstance()
        var user = firebaseAuth.currentUser
        var uid = user!!.uid

        Glide.with(activity)  //2
            .load(list[position]?.pimage) //3
            .centerCrop() //4
            .into(holder.mimage) //8

        holder.mdelete.setOnClickListener {
            ref.child("Cart").child(uid.toString()).child(list.get(position).key.toString()).removeValue()

        }


    }

    override fun getItemCount(): Int {
        return list.size
    }

    class ViewDate(itemView: View) : Recycleview.ViewHoler(itemView) {
        var mname = itemView.findViewById<TextView>(R.id.cart_name_txt)
        var mprice = itemView.findViewById<TextView>(R.id.cart_price_txt)
        var mcat = itemView.findViewById<TextView>(R.id.cart_category_txt)
        var mdes = itemView.findViewById<TextView>(R.id.cart_des_txt)
        var mimage = itemView.findViewById<ImageView>(R.id.cart_Image_rv)
        var mdelete = itemView.findViewById<ImageView>(R.id.cart_delete_btn)
    }

}