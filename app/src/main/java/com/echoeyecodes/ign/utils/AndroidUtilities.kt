package com.echoeyecodes.dobby.utils

import android.content.Context
import android.graphics.Bitmap
import android.os.Environment
import android.util.Log
import android.widget.Toast
import java.io.File
import java.io.FileOutputStream

class AndroidUtilities{

    companion object{
        fun showToastMessage(context: Context, message:String) = Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
        fun log(message: String) = Log.d("CARRR", message)
        fun getAppDirectory() = File(Environment.getExternalStorageDirectory(), "/Dobby")
    }

}