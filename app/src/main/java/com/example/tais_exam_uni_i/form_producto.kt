package com.example.tais_exam_uni_i

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.firestore.FirebaseFirestore


class form_producto : AppCompatActivity() {
    private val SELECT_ACTIVITY = 50
    private var imageUri: Uri? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_form_producto)

        val items = arrayOf(
                "Lacteos",
                "Energizante",
                "Gaseosa",
                "Cerveza"
        )

        val adaptermenu = ArrayAdapter<String>(this, R.layout.activity_element,items)
        findViewById<AutoCompleteTextView>(R.id.txtCategoria).setAdapter(adaptermenu)

        findViewById<ImageView>(R.id.image_producto).setOnClickListener {
            ImageController.selectPhotoFromGallery(activity = this, code = SELECT_ACTIVITY)
        }

        findViewById<Button>(R.id.btnEliminar).isVisible = false
        val data = this.intent.extras
        if(data!=null){
            findViewById<Button>(R.id.btnEliminar).isVisible = true
            val ide = data.getString("id")
            val descripcion = data.getString("descripcion")
            val categoria = data.getString("categoria")
            val precio = data.getString("precio")
            val stock = data.getString("stock")
            val imagen = ImageController.getImagen(this,ide)
            Log.d("image", imagen.toString())
            findViewById<ImageView>(R.id.image_producto).setImageURI(imagen)
            findViewById<EditText>(R.id.txtDescripcion).setText(descripcion)
            findViewById<EditText>(R.id.txtCategoria).setText(categoria)
            findViewById<EditText>(R.id.txtPrecio).setText(precio)
            findViewById<EditText>(R.id.txtStock).setText(stock)
        }
        saveData()


    }

    private fun saveData() {
        val data = this.intent.extras
        var opt = 1
        var ide:String = ""
        val ref = FirebaseFirestore.getInstance()

        if(data!=null){
            ide = data.getString("id").toString()
            opt = 2
        }

        findViewById<Button>(R.id.btnRegister).setOnClickListener {
            val descripcion = findViewById<EditText>(R.id.txtDescripcion).text.toString()
            val categoria = findViewById<EditText>(R.id.txtCategoria).text.toString()
            val precio = findViewById<EditText>(R.id.txtPrecio).text.toString()
            val stock = findViewById<EditText>(R.id.txtStock).text.toString()

            val producto = Producto("",imageUri.toString(),descripcion,categoria,precio,stock)


            if(opt==1) {
                ref.collection("xproducto").add(producto).addOnSuccessListener { document ->
                    Toast.makeText(applicationContext, "Producto Registrado", Toast.LENGTH_LONG).show()

                    imageUri?.let {
                        ImageController.saveImage(this@form_producto, document.id, it)
                    }
                    finish()
                }
            }
            else if(opt==2){
               ref.collection("xproducto").document(ide).set(producto).addOnSuccessListener {
                   Toast.makeText(applicationContext, "Cambios guardados", Toast.LENGTH_LONG).show()

                   imageUri?.let {
                       ImageController.saveImage(this@form_producto, ide.toString(), it)
                   }
                   finish()
               }
            }

        }

        if(data!=null){
            findViewById<Button>(R.id.btnEliminar).setOnClickListener {
                ref.collection("xproducto").document(ide).delete().addOnSuccessListener {
                    Toast.makeText(applicationContext, "Producto Eliminado", Toast.LENGTH_LONG).show()
                    ImageController.deleteImage(this,ide)
                }
                finish()
            }
        }

    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        when{
            requestCode == SELECT_ACTIVITY && resultCode == Activity.RESULT_OK -> {
                imageUri = data!!.data
                Log.d("image", imageUri.toString())
                findViewById<ImageView>(R.id.image_producto).setImageURI(imageUri)
            }
        }
    }
}