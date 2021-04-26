package com.echoeyecodes.dobby.activities

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.echoeyecodes.ign.MainActivity
import com.echoeyecodes.ign.activities.SignUpActivity

class SplashScreenActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val sharedPref = getSharedPreferences("auth", Context.MODE_PRIVATE)
        val token = sharedPref.getString("token", null)

        if(token != null){
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }else{
            val intent = Intent(this, SignUpActivity::class.java)
            startActivity(intent)
        }
        finish()
    }
}