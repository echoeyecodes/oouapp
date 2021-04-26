package com.echoeyecodes.dobby.utils

import android.content.res.Resources
import android.graphics.Color
import android.util.TypedValue
import java.time.LocalDateTime


fun Int.convertToDp() : Int {
    return TypedValue.applyDimension(
        TypedValue.COMPLEX_UNIT_DIP,
        this.toFloat(),
        Resources.getSystem().displayMetrics
    )
        .toInt()
}

fun String.convertHexToColor(): Int {
    return Color.parseColor(this)
}

fun String.getFormattedDate():String{
    return try{
        val timestamp = LocalDateTime.parse(this)
        "${timestamp.dayOfMonth}-${timestamp.monthValue}-${timestamp.year}"
    }catch(exception:Exception){
        "N/A"
    }
}

fun String.getGradeValue(): Int {
    return when(this){
        "A", "a" -> 5
        "B", "b" -> 4
        "C", "c" -> 3
        "D", "d" -> 2
        "E", "e" -> 1
        else -> 0
    }
}