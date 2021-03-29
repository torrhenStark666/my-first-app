package com.alecsander.poc.view

import android.content.Intent
import android.content.Intent.*
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.alecsander.poc.R
import com.google.firebase.auth.FirebaseAuth

class ForgotPasswdActivity : AppCompatActivity() {

    private var etUser          : EditText?         = null
    private var etSubmit        : Button?           = null

    private var mAuth           :FirebaseAuth?      = null

    private val tag             = "ForgotPasswdActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_forgot_passwd)

        initialize()
    }

    private fun initialize(){

        etUser = findViewById<EditText>(R.id.et_user)
        etSubmit = findViewById<Button>(R.id.btn_submit)

        mAuth = FirebaseAuth.getInstance()

        etSubmit!!.setOnClickListener{ rememberMe() }

    }

    private fun rememberMe(){

        val user = etUser?.text.toString()

        if(user.isNullOrBlank()){
            Toast.makeText(this@ForgotPasswdActivity, "Fill in the field", Toast.LENGTH_SHORT).show()
        }
        Log.d(tag, "Enviando solicitacao")
        mAuth!!
            .sendPasswordResetEmail(user)
            .addOnCompleteListener(this){task ->

                if(task.isSuccessful){
                    Log.d(tag,"Sucesso!")
                    Toast.makeText(this@ForgotPasswdActivity, "Email sanded", Toast.LENGTH_SHORT).show()
                    updateUI()
                }else{
                    Log.d(tag, "Falha!")
                    Toast.makeText(this@ForgotPasswdActivity, "Fail, email invalid", Toast.LENGTH_SHORT).show()
                }

            }

    }

    private fun updateUI(){
        val intent = Intent(this@ForgotPasswdActivity, LoginActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        startActivity(intent)
    }

}