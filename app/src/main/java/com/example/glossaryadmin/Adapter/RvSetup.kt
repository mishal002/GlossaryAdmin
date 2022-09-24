import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.glossaryadmin.Admin_Activity.A_Read_Data
import com.example.glossaryadmin.Admin_Activity.dbshowhelper
import com.example.glossaryadmin.R
import com.example.glossaryadmin.Admin_Activity.Updata_data
import com.google.firebase.database.FirebaseDatabase
import java.util.ArrayList


class Recycleview(val activity: A_Read_Data, val list: ArrayList<dbshowhelper>) :
    RecyclerView.Adapter<Recycleview.ViewHoler>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHoler {
        var view = LayoutInflater.from(parent.context).inflate(R.layout.a_showdata_itemfile, parent, false)
        return ViewHoler(view)
    }

    override fun onBindViewHolder(holder: ViewHoler, position: Int) {
        holder.id.text = list[position].id
        holder.name.text = list[position].name
        holder.category.text = list[position].category
        holder.price.text = list[position].price
        holder.des.text = list[position].des

        var firebaseDatabase = FirebaseDatabase.getInstance()
        var ref = firebaseDatabase.reference

// photo
        Glide.with(activity)  //2
            .load(list[position]?.image) //3
            .centerCrop() //4
            .into(holder.image) //8

//        click
        holder.delete.setOnClickListener {

            ref.child("Product").child(list.get(position).key.toString()).removeValue()
        }
        holder.edit.setOnClickListener {

            var intent = Intent(activity, Updata_data::class.java)
            intent.putExtra("n1", list[position].id)
            intent.putExtra("n2", list[position].name)
            intent.putExtra("n3", list[position].category)
            intent.putExtra("n4", list[position].price)
            intent.putExtra("n5", list[position].des)
            intent.putExtra("n6",list[position].key)
            intent.putExtra("n7",list[position].image)

            activity.startActivity(intent)
        }
        holder.share_btn.setOnClickListener {
            Toast.makeText(activity, "Success", Toast.LENGTH_SHORT).show()
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }

    open class ViewHoler(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var id = itemView.findViewById<TextView>(R.id.product_id_txt)
        var name = itemView.findViewById<TextView>(R.id.product_name_txt)
        var category = itemView.findViewById<TextView>(R.id.category_txt)
        var price = itemView.findViewById<TextView>(R.id.price_txt)
        var des = itemView.findViewById<TextView>(R.id.des_txt)
        var delete = itemView.findViewById<ImageView>(R.id.delete_btn)
        var edit = itemView.findViewById<ImageView>(R.id.edit_btn)
        var image = itemView.findViewById<ImageView>(R.id.Image_rv)
        var share_btn = itemView.findViewById<ImageView>(R.id.share_btn)
    }
}