package com.echoeyecodes.scrub.utils

import com.github.mikephil.charting.formatter.ValueFormatter
import kotlin.math.roundToInt

class PercentValueFormatter(private val total:Int) : ValueFormatter() {

    override fun getFormattedValue(value: Float): String {
            return "${(value).roundToInt()}%"
    }

}