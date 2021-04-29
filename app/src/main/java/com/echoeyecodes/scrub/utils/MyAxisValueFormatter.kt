package com.echoeyecodes.scrub.utils

import com.github.mikephil.charting.components.AxisBase
import com.github.mikephil.charting.formatter.IAxisValueFormatter
import com.github.mikephil.charting.formatter.ValueFormatter
import java.text.DecimalFormat


class MyAxisValueFormatter : ValueFormatter() {

    override fun getFormattedValue(value: Float): String {
        return value.toInt().toString()
    }

}