package com.alecsander.poc.view

import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.alecsander.poc.ContactAdapter
import com.alecsander.poc.R
import com.alecsander.poc.model.Contact
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*

class MainActivity : AppCompatActivity() {
    private var btnCadastrar        : Button? = null

    private var mDatabaseReference  : DatabaseReference?    = null
    private var mDatabase           : FirebaseDatabase?     = null
    private var mAuth               : FirebaseAuth?         = null

    private val tag                 = "MainActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initialize()
    }

    private fun initialize(){
        btnCadastrar        = findViewById<Button>(R.id.btn_cad)

        mDatabase           = FirebaseDatabase.getInstance()
        mDatabaseReference  = mDatabase!!.reference!!.child("contacts")
        mAuth               = FirebaseAuth.getInstance()

        btnCadastrar!!.setOnClickListener {
            startActivity(Intent(this@MainActivity, CreateContactActivity::class.java)) }

        loadList {list ->
            Log.d(tag, "Chamando Adapter.")
            val recyclerView = findViewById<RecyclerView>(R.id.note_list_recyclerview)
            recyclerView.adapter = ContactAdapter(list, this)
            val layoutManager = LinearLayoutManager(this)
            recyclerView.layoutManager = layoutManager

            (recyclerView.adapter as ContactAdapter)?.onItemClick = { contact ->

                Log.d(tag, "Item click")

            }

        }

    }

    private fun loadList(callback: (list: List<Contact>) -> Unit) {
        mDatabaseReference?.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onCancelled(snapshotError: DatabaseError) {
                Log.d(tag, "Falha ao carregar lista.")
            }

            override fun onDataChange(snapshot: DataSnapshot) {
                Log.d(tag, "Carregou a Lista.")
                val list : MutableList<Contact> = mutableListOf()
                val children = snapshot!!.children
                children.forEach {
                    val userId = mAuth?.currentUser?.uid?.toString()
                    val id: String? = it.child("userId").getValue(String::class.java)
                    val name: String? = it.child("nameContact").getValue(String::class.java)
                    val email: String? = it.child("email").getValue(String::class.java)
                    if( userId.equals(id) ) {
                        list.add(Contact(id, name, email))
                    }
                }
                callback(list)
            }
        })
    }

}