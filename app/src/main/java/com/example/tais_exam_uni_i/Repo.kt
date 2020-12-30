package com.example.tais_exam_uni_i

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QueryDocumentSnapshot

class Repo {
    fun getProductoData():LiveData<MutableList<Producto>>{
        val mutableData = MutableLiveData<MutableList<Producto>>()
        val listData:MutableList<Producto> = mutableListOf<Producto>()
        val db = FirebaseFirestore.getInstance()
        db.collection("xproducto").get().addOnSuccessListener {
                result->
            //Log.d("result", result.toString())
            for (document in result){
                //Log.d("TAG", "${document.id} => ${document.data}")
                val imagenurl:String? = document.getString("imagenurl")
                val descripcion:String? = document.getString("descripcion")
                val categoria:String? = document.getString("categoria")
                val precio:String? = document.getString("precio")
                val stock:String? = document.getString("stock")
                val producto = Producto(document.id,imagenurl!!,descripcion!!, categoria!!,precio!!,stock!!)
                listData.add(producto)
            }
            mutableData.value = listData
        }
        return mutableData
    }
}