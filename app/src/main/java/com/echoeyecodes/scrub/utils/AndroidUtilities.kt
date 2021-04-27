package com.echoeyecodes.scrub.utils

import android.content.Context
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.core.content.res.ResourcesCompat
import com.echoeyecodes.scrub.R
import com.google.android.material.snackbar.Snackbar

class AndroidUtilities{

    companion object{
        fun showToastMessage(context: Context, message:String) = Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
        fun log(message: String) = Log.d("CARRR", message)
        fun showSnackBar(view: View, message: String) = run {
            val snackBar = Snackbar.make(view, message, Snackbar.LENGTH_SHORT)
            snackBar.setAction("Okay") {
                snackBar.dismiss()
            }.setActionTextColor(ResourcesCompat.getColor(view.resources, R.color.white, null))
            snackBar.show()
        }
    }

}