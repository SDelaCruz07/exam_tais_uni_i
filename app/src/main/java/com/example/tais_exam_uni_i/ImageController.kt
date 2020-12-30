package com.example.tais_exam_uni_i

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.util.Log
import java.io.File

object ImageController {
    fun selectPhotoFromGallery(activity:Activity, code:Int){
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        activity.startActivityForResult(intent,code)
    }

    fun saveImage(context: Context,id:String,uri:Uri){
        val file = File(context.filesDir,id)
        Log.d("**********************************URIII", file.toString())
        val bytes = context.contentResolver.openInputStream(uri)?.readBytes()!!

        file.writeBytes(bytes)
    }

    fun getImagen(context: Context, id: String?): Uri{
        val file = File(context.filesDir,id)

        return if (file.exists()) Uri.fromFile(file)
        else Uri.parse("android.resource://com.example.tais_exam_uni_i/drawable/placeholder")
    }

    fun deleteImage(context: Context, id: String?){
        val file = File(context.filesDir,id)
        file.delete()
    }

}