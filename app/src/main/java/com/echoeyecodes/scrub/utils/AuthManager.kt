package com.echoeyecodes.scrub.utils

import android.app.Activity
import android.content.Context
import android.content.Intent
import com.echoeyecodes.scrub.activities.SignUpActivity
import com.echoeyecodes.scrub.repositories.CourseRepository
import kotlinx.coroutines.runBlocking

class AuthManager {
    fun logOut(){
        val context = MyApplication.myContext

        val courseRepository = CourseRepository(context!!)
        runBlocking {
            courseRepository.deleteData()
            val sharedPref = context.getSharedPreferences("auth", Context.MODE_PRIVATE)

            sharedPref.edit().apply {
                remove("token")
                apply()
                context.startActivity(Intent(context, SignUpActivity::class.java))
                (context as Activity).finish()
            }
        }
    }
}