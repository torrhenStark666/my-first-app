package com.alecsander.poc.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.alecsander.poc.R
import com.alecsander.poc.model.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class CreateAccActivity : AppCompatActivity() {

    private var etName             : EditText?             = null
    private var etUser             : EditText?             = null
    private var etPassword         : EditText?             = null
    private var btnNewAcc          : Button?               = null

    private var mDatabaseReference  : DatabaseReference?    = null
    private var mDatabase           : FirebaseDatabase?     = null
    private var mAuth               : FirebaseAuth?         = null

    private var name                : String?               = null
    private var user                : String?               = null
    private var password            : String?               = null

    private val tag                 = "CreateAccActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_acc)

        initialize()
    }

    private fun initialize(){
        etName      = findViewById<EditText>(R.id.et_name)
        etUser      = findViewById<EditText>(R.id.et_user)
        etPassword  = findViewById<EditText>(R.id.et_password)
        btnNewAcc   = findViewById<Button>(R.id.btn_new_acc)

        mDatabase           = FirebaseDatabase.getInstance()
        mDatabaseReference  = mDatabase!!.reference!!.child("Users")
        mAuth               = FirebaseAuth.getInstance()

        btnNewAcc!!.setOnClickListener { createNewAccount() }
    }

    private fun createNewAccount(){

        name = etName?.text.toString()
        user = etUser?.text.toString()
        password = etPassword?.text.toString()

        if(name.isNullOrBlank() && user.isNullOrBlank() && password.isNullOrBlank()){
            Toast.makeText(this@CreateAccActivity, "Fill in the fields", Toast.LENGTH_SHORT).show()
        }

        mAuth
            ?.createUserWithEmailAndPassword(user!!, password!!)
            ?.addOnCompleteListener(this){ task ->
                if(task.isSuccessful){
                    Log.d(tag, "createUserWithEmail:success")
                    val userId = mAuth!!.currentUser!!.uid
                    verifyEmail();
                    val currentUserDb = mDatabaseReference!!.child(userId)
                    currentUserDb.child("firstName").setValue(name)
                    updateUserInfoAndUI()
                }else{
                    Log.w(tag, "createUserWithEmail:failure", task.exception)
                    Toast.makeText(this@CreateAccActivity, "Authentication failed.",
                        Toast.LENGTH_SHORT).show()
                }
            }
    }

    private fun updateUserInfoAndUI() {
        val intent = Intent(this@CreateAccActivity, MainActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        startActivity(intent)
    }

    private fun verifyEmail() {
        val mUser = mAuth!!.currentUser;
        mUser!!.sendEmailVerification()
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    Toast.makeText(this@CreateAccActivity,
                        "Verification email sent to " + mUser.email,
                        Toast.LENGTH_SHORT).show()
                } else {
                    Log.e(tag, "sendEmailVerification", task.exception)
                    Toast.makeText(this@CreateAccActivity,
                        "Failed to send verification email.",
                        Toast.LENGTH_SHORT).show()
                }
            }
    }
}