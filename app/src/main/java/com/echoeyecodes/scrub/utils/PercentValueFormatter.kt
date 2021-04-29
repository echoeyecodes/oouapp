package com.echoeyecodes.scrub.utils

import com.github.mikephil.charting.formatter.ValueFormatter
import kotlin.math.roundToInt

class PercentValueFormatter(val total:Int) : ValueFormatter() {

    override fun getFormattedValue(value: Float): String {
        val result = ((value/100) * total).roundToInt()
        return if (result == 1){
            "$result course"
        }else{
            "$result courses"
        }
    }

}