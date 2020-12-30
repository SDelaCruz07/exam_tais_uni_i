package com.example.tais_exam_uni_i

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class MainAdapter(private val context: Context, var clickListener: ClickListener): RecyclerView.Adapter<MainAdapter.MainViewHolder>() {

    private var datalist = mutableListOf<Producto>()

    fun setListData(data: MutableList<Producto>){
        datalist = data
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.element,parent,false)
        return MainViewHolder(view, clickListener)
    }

    override fun getItemCount(): Int {
        return if (datalist.size>0){
            datalist.size
        }
        else{
            0
        }
    }

    override fun onBindViewHolder(holder: MainViewHolder, position: Int) {
        val producto: Producto = datalist[position]
        holder.bindview(producto)
    }

    inner class MainViewHolder(itemView:View, listener: ClickListener):RecyclerView.ViewHolder(itemView),View.OnClickListener{

        var listener:ClickListener?=null
        init {
            this.listener = listener
            itemView.setOnClickListener(this)
        }

        fun bindview(producto: Producto){
            val imagen = ImageController.getImagen(context,producto.id)
            itemView.findViewById<ImageView>(R.id.iconImagen).setImageURI(imagen)
            itemView.findViewById<TextView>(R.id.txt_descripcion).setText(producto.descripcion)
            itemView.findViewById<TextView>(R.id.txt_precio).setText("S/. "+producto.precio)
            itemView.findViewById<TextView>(R.id.txt_categoria).setText(producto.categoria)
        }

        override fun onClick(v: View?) {
            this.listener?.onclick(itemView,adapterPosition)
        }
    }

}