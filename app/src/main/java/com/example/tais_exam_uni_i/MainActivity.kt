package com.example.tais_exam_uni_i

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.facebook.shimmer.ShimmerFrameLayout
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class MainActivity : AppCompatActivity() {
    private lateinit var productBD:DatabaseReference
    private lateinit var productoListener:ValueEventListener
    private lateinit var mRecyclerView : RecyclerView
    private lateinit var adapter:MainAdapter
    var listData:MutableList<Producto> = mutableListOf<Producto>()
    private val viewModel by lazy { ViewModelProviders.of(this).get(MainViewModel::class.java) }




    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(findViewById(R.id.toolbar))
        //action button floating
        findViewById<FloatingActionButton>(R.id.fab).setOnClickListener {
            val intentAdd = Intent(applicationContext, form_producto::class.java)
            startActivity(intentAdd)
        }



    }

    override fun onStart() {
        super.onStart()
        findViewById<ShimmerFrameLayout>(R.id.shimmer_view_container).startShimmer()
        oberveData()
        adapter = MainAdapter(this,object : ClickListener{
            override fun onclick(vista: View, position: Int) {
                val intentAdd = Intent(applicationContext, form_producto::class.java)
                intentAdd.putExtra("id",listData?.get(position)?.id)
                intentAdd.putExtra("descripcion",listData?.get(position)?.descripcion)
                intentAdd.putExtra("categoria",listData?.get(position)?.categoria)
                intentAdd.putExtra("precio",listData?.get(position)?.precio)
                intentAdd.putExtra("stock",listData?.get(position)?.stock)
                intentAdd.putExtra("imagenurl",listData?.get(position)?.imagenurl)
                startActivity(intentAdd)
                //Toast.makeText(applicationContext, listData?.get(position)?.descripcion , Toast.LENGTH_SHORT).show()
            }

        })

        //productBD = FirebaseDatabase.getInstance().getReference("productos")
        mRecyclerView = findViewById(R.id.listProducto)
        mRecyclerView.layoutManager = LinearLayoutManager(this)
        mRecyclerView.adapter = adapter
        //adapter.setListData(listData)
        //adapter.notifyDataSetChanged()



    }

    fun oberveData(){
        viewModel.fetchProductData().observe(this, Observer {
            listData = it
            findViewById<ShimmerFrameLayout>(R.id.shimmer_view_container).stopShimmer()
            findViewById<ShimmerFrameLayout>(R.id.shimmer_view_container).hideShimmer()
            findViewById<ShimmerFrameLayout>(R.id.shimmer_view_container).visibility = View.GONE
            adapter.setListData(it)
            adapter.notifyDataSetChanged()
        })
    }



    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }
}