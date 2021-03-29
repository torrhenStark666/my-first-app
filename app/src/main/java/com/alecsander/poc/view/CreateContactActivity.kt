package com.alecsander.poc.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.alecsander.poc.R
import com.alecsander.poc.model.Contact
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class CreateContactActivity : AppCompatActivity() {

    private var etName             : EditText?             = null
    private var etEmail            : EditText?             = null
    private var btnNewContact      : Button?               = null

    private var mDatabaseReference  : DatabaseReference?    = null
    private var mDatabase           : FirebaseDatabase?     = null
    private var mAuth               : FirebaseAuth?         = null

    private val tag                 = "CreateContactActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_contact)

        initialize()
    }

    private fun initialize(){
        etName      = findViewById<EditText>(R.id.et_name)
        etEmail     = findViewById<EditText>(R.id.et_email)
        btnNewContact= findViewById<Button>(R.id.btn_new_contact)

        mDatabase           = FirebaseDatabase.getInstance()
        mDatabaseReference  = mDatabase!!.reference!!
        mAuth               = FirebaseAuth.getInstance()

        btnNewContact!!.setOnClickListener { createNewContact() }
    }

    private fun createNewContact(){
        val contact = Contact(null,etName?.text.toString(), etEmail?.text.toString())
        val key = mDatabaseReference?.push()?.key
        Log.d(tag, "Iniciando")

        mDatabaseReference!!.child("contacts").child(key.toString()).setValue(contact)?.
            addOnCompleteListener(this){task ->

                if(task.isSuccessful){
                    Toast.makeText(this@CreateContactActivity, "Contact created", Toast.LENGTH_SHORT).show()
                    Log.d(tag, "Sucesso!")
                    updateIU()
                }else{
                    Toast.makeText(this@CreateContactActivity, "Contact not created", Toast.LENGTH_SHORT).show()
                    Log.d(tag, "falha", task.exception)
                }

            }

    }

    private fun updateIU(){
        val intent = Intent(this@CreateContactActivity, MainActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        startActivity(intent)
    }
}