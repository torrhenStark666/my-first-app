package com.alecsander.poc.view

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.Window
import android.view.WindowManager
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.alecsander.poc.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class LoginActivity : AppCompatActivity() {

    private var etUser             : EditText?             = null
    private var etPassword         : EditText?             = null
    private var btnNewAcc          : Button?               = null
    private var btnLogin           : Button?               = null
    private var tvRememberPass     : TextView?             = null

    private var mAuth               : FirebaseAuth?         = null

    private var user                : String?               = null
    private var password            : String?               = null

    private val tag                 = "LoginActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
            window.setStatusBarColorTo(R.color.primary)

        initialize()
    }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    fun Window.setStatusBarColorTo(color: Int){
        this.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
        this.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        this.statusBarColor = ContextCompat.getColor(baseContext, color)
    }

    private fun initialize(){

        etUser = findViewById<EditText>(R.id.et_user)
        etPassword= findViewById<EditText>(R.id.et_password)
        btnLogin= findViewById<Button>(R.id.btn_login)
        btnNewAcc=findViewById<Button>(R.id.btn_new_acc)
        tvRememberPass=findViewById<TextView>(R.id.tv_remember_pass)

        mAuth = FirebaseAuth.getInstance();

        btnNewAcc!!
            .setOnClickListener {
                startActivity(Intent(this@LoginActivity, CreateAccActivity::class.java)) }
        tvRememberPass!!
            .setOnClickListener {
                startActivity(Intent(this@LoginActivity, ForgotPasswdActivity::class.java)) }
        btnLogin!!
            .setOnClickListener{ access() }

    }

    private fun access(){

        user = etUser?.text.toString()
        password = etPassword?.text.toString()

        Log.d(tag, "Logando usuario")

        if(!user.isNullOrBlank() && !password.isNullOrBlank()){

            mAuth
                ?.signInWithEmailAndPassword(user!!, password!!)
                ?.addOnCompleteListener(this){task ->

                    if(task.isSuccessful){
                        Log.d(tag, "Sucesso!")
                        updateUI()
                    }else{
                        Log.d(tag, "Falha!", task.exception)
                        Toast.makeText(this@LoginActivity, "Authentication Fail.", Toast.LENGTH_SHORT).show()
                    }

                }

        }else{
            Toast.makeText(this@LoginActivity, "Fill in the fields.", Toast.LENGTH_LONG).show()
        }
    }

    private fun updateUI(){
        val intent = Intent(this@LoginActivity, MainActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        startActivity(intent)
    }

}