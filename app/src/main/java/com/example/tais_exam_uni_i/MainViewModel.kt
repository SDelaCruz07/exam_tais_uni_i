package com.example.tais_exam_uni_i

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MainViewModel:ViewModel() {
    private val repo = Repo()
    fun fetchProductData():LiveData<MutableList<Producto>>{
        val mutableData = MutableLiveData<MutableList<Producto>>()
        repo.getProductoData().observeForever{productoList->
            mutableData.value = productoList

        }
        return mutableData
    }
}