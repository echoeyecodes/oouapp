package com.echoeyecodes.scrub.utils

import com.github.mikephil.charting.charts.BarLineChartBase
import com.github.mikephil.charting.charts.Chart
import com.github.mikephil.charting.components.AxisBase
import com.github.mikephil.charting.data.BarEntry
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.formatter.IAxisValueFormatter
import com.github.mikephil.charting.formatter.ValueFormatter


class DayAxisValueFormatter(val data: List<String>) : ValueFormatter() {
    override fun getFormattedValue(value: Float): String {
        if(data.isEmpty()){
            return ""
        }
        return data[value.toInt()-1]
    }
}