package com.shubham.shayariadmin

import android.app.Dialog
import android.graphics.drawable.ColorDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.firestore.FirebaseFirestore
import com.shubham.shayariadmin.databinding.ActivityAllShayariBinding
import com.shubham.shayariadmin.databinding.DilogAddCategoryBinding
import com.shubham.shayariadmin.databinding.DilogAddShayariBinding
import com.shubham.shayariapp.Adapter.AllShayariAdapter
import com.shubham.shayariapp.Models.ShayariModels

lateinit var binding: ActivityAllShayariBinding;
lateinit var db: FirebaseFirestore;

class All_Shayari_Activity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAllShayariBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()

        val name = intent.getStringExtra("name")
        val id = intent.getStringExtra("id")

        db = FirebaseFirestore.getInstance()
        db.collection("Shayari").document(id!!).collection("all")
            .addSnapshotListener { value, error ->
                val shayariList = arrayListOf<ShayariModels>()
                val data = value?.toObjects(ShayariModels::class.java)
                shayariList.addAll(data!!)
                binding.rcvAllShayari.layoutManager = LinearLayoutManager(this)
                binding.rcvAllShayari.adapter = AllShayariAdapter(this, shayariList, id)
            }

        binding.btnAddShayari.setOnClickListener {
            val addCatDialog = Dialog(this@All_Shayari_Activity)
            val binding = DilogAddShayariBinding.inflate(layoutInflater)
            addCatDialog.setContentView(binding.root)

            if (addCatDialog.window != null) {
                addCatDialog.window!!.setBackgroundDrawable(ColorDrawable(0))
            }

            binding.dialogAddShayari.setOnClickListener {
                val uid = db.collection("Shayari").document().id

                val edtShayariget = binding.dialogShayari.text.toString()
                val finalValue = ShayariModels(uid, edtShayariget)
                db.collection("Shayari")
                    .document(id).collection("all")
                    .document(uid).set(finalValue).addOnCompleteListener {
                        if (it.isSuccessful) {
                            addCatDialog.dismiss()
                           Toast.makeText(this@All_Shayari_Activity,"Shayari Added Successfully",Toast.LENGTH_SHORT).show()
                        }

                    }

            }

            addCatDialog.show()
        }

    }
}