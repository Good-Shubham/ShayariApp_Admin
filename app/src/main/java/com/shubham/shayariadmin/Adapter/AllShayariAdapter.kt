package com.shubham.shayariapp.Adapter


import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.firestore.FirebaseFirestore
import com.shubham.shayariadmin.All_Shayari_Activity
import com.shubham.shayariadmin.databinding.ItermShayariBinding
import com.shubham.shayariapp.Models.ShayariModels

val db=FirebaseFirestore.getInstance()

class AllShayariAdapter(
    val allShayariActivity: All_Shayari_Activity,
    val shayariList: ArrayList<ShayariModels>,
    val catid: String
) : RecyclerView.Adapter<AllShayariAdapter.ShayariViewHolder>() {
    class ShayariViewHolder(val binding: ItermShayariBinding) :
        RecyclerView.ViewHolder(binding.root)


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ShayariViewHolder {


        return ShayariViewHolder(
            ItermShayariBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ShayariViewHolder, position: Int) {


        holder.binding.itemShayari.text = shayariList[position].data.toString()
        holder.binding.btnDelete.setOnClickListener{
            db.collection("Shayari").document(catid).
            collection("all").document(shayariList[position].id!!).delete().addOnCompleteListener{
                Toast.makeText(allShayariActivity,"Shayari Delete",Toast.LENGTH_SHORT).show()
            }

        }


    }

    override fun getItemCount() = shayariList.size
}