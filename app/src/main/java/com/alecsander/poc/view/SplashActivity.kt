package com.alecsander.poc.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import com.alecsander.poc.R

class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        changeLogin()
    }

    fun changeLogin(){
        val intent = Intent(this, LoginActivity::class.java)

        Handler(Looper.getMainLooper()).postDelayed({
            intent.change()
        }, 2000)
    }

    fun Intent.change(){
        startActivity(this)
        finish()
    }
}