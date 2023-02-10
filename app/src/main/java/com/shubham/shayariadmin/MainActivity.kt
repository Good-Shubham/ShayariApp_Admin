package com.shubham.shayariadmin

import android.app.Dialog
import android.graphics.drawable.ColorDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.firestore.FirebaseFirestore
import com.shubham.shayariadmin.databinding.ActivityMainBinding
import com.shubham.shayariadmin.databinding.DilogAddCategoryBinding
import com.shubham.shayariapp.Adapter.CategoryAdapter
import com.shubham.shayariapp.Models.CatModels

class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding
    lateinit var db:FirebaseFirestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide()

        db = FirebaseFirestore.getInstance()

        binding.btnAddCategory.setOnClickListener{

           val addCatDialog = Dialog(this@MainActivity)
            val binding=DilogAddCategoryBinding.inflate(layoutInflater)
            addCatDialog.setContentView(binding.root)

            if (addCatDialog.window != null){
                addCatDialog.window!!.setBackgroundDrawable(ColorDrawable(0))
            }

            binding.dialogAddCat.setOnClickListener{
                val  name = binding.dialogCatName.text.toString()
                val id = db.collection("Shayari").document().id
                val data = CatModels(id,name)
                db.collection("Shayari").document(id).set(data).addOnSuccessListener {
                    Toast.makeText(this@MainActivity,"Shayari Category Add",Toast.LENGTH_SHORT).show()
                    addCatDialog.dismiss()
                }.addOnCanceledListener {
                    Toast.makeText(this@MainActivity,""+it.toString(), Toast.LENGTH_SHORT).show()
                }
            }

            addCatDialog.show()
        }

        db.collection("Shayari").addSnapshotListener { value, error ->
            val  list= arrayListOf<CatModels>()
            val data = value?.toObjects(CatModels::class.java)
            list.addAll(data!!)
            binding.rcvCategory.layoutManager= LinearLayoutManager(this)
            binding.rcvCategory.adapter= CategoryAdapter(this,list)

        }


    }

}