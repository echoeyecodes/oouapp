package com.echoeyecodes.scrub.utils

import android.app.Application
import android.content.Context
import androidx.room.Room
import com.echoeyecodes.scrub.room.database.CourseDatabase

class MyApplication : Application() {

    companion object{
        @Volatile
        var myContext: Context? = null
    }
}