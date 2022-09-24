package com.example.glossaryadmin.Adapter

import Recycleview
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.glossaryadmin.Admin_Activity.dbshowhelper
import com.example.glossaryadmin.R
import com.example.glossaryadmin.UserFragment.Category_Fragment

class AdapterCategory(val Fragment: Category_Fragment, val list: ArrayList<dbshowhelper>) :
    RecyclerView.Adapter<AdapterCategory.Viewdata>() {




    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Viewdata {
        var view = LayoutInflater.from(parent.context).inflate(R.layout.u_read_itemfiile, parent, false)
        return Viewdata(view)
    }

    override fun onBindViewHolder(holder: Viewdata, position: Int) {
        holder.Cid.text = list[position].id
        holder.Cname.text = list[position].name
        holder.Ccategory.text = list[position].category
        holder.Cprice.text = list[position].price
        holder.Cdes.text = list[position].des

// photo
        Glide.with(Fragment)  //2
            .load(list[position]?.image) //3
            .centerCrop() //4
            .into(holder.Cimage) //8

//        holder.Ccart_btn.setOnClickListener {
//            add(Category)
//            Toast.makeText(Fragment.activity,
//                "Add item  :- " + list.get(position).name,
//                Toast.LENGTH_SHORT).show()
//        }

    }

    override fun getItemCount(): Int {
        return list.size
    }

    class Viewdata(itemView: View) : Recycleview.ViewHoler(itemView) {

        var Cid = itemView.findViewById<TextView>(R.id.product_id_txt)
        var Cname = itemView.findViewById<TextView>(R.id.product_name_txt)
        var Ccategory = itemView.findViewById<TextView>(R.id.category_txt)
        var Cprice = itemView.findViewById<TextView>(R.id.price_txt)
        var Cdes = itemView.findViewById<TextView>(R.id.des_txt)
        var Cimage = itemView.findViewById<ImageView>(R.id.Image_rv)

//        var Ccart_btn = itemView.findViewById<ImageView>(R.id.cart_btn)
    }


}