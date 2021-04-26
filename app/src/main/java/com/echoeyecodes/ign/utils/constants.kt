package com.echoeyecodes.dobby.utils

import android.content.Context
import android.content.res.Resources
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.echoeyecodes.ign.R

class Dimensions(val width:Int, val height:Int)

fun getNavigationHeight() : Int {
    val resources = Resources.getSystem()
    val resource = resources.getIdentifier("navigation_bar_height", "dimen", "android")
    if(resource > 0){
        return resources.getDimensionPixelSize(resource)
    }
    return 0
}

fun bottomNavigationViewHeight() : Int{
    return 56.convertToDp()
}

fun getScreenSize():Dimensions{
    return Dimensions(Resources.getSystem().displayMetrics.widthPixels, Resources.getSystem().displayMetrics.heightPixels)
}

fun getCircularProgressDrawable(context:Context):CircularProgressDrawable{
    return CircularProgressDrawable(context).apply {
        strokeWidth = 1.5F
        centerRadius = 30f
        setColorSchemeColors(R.color.colorPrimary, R.color.colorPrimary, R.color.colorPrimary)
        start()
    }
}

