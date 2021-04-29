package com.echoeyecodes.scrub.adapters.chartadapters

import android.graphics.DashPathEffect
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.res.ResourcesCompat
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.echoeyecodes.dobby.utils.BarChartItemCallBack
import com.echoeyecodes.dobby.utils.DefaultItemCallBack
import com.echoeyecodes.scrub.R
import com.echoeyecodes.scrub.models.BarChartModel
import com.echoeyecodes.scrub.utils.DayAxisValueFormatter
import com.echoeyecodes.scrub.utils.MyAxisValueFormatter
import com.github.mikephil.charting.charts.BarChart
import com.github.mikephil.charting.components.Legend
import com.github.mikephil.charting.components.LegendEntry
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.components.YAxis
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet

class BarChartAdapter : ListAdapter<BarChartModel, BarChartAdapter.BarChartViewHolder>(BarChartItemCallBack()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BarChartViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.layout_bar_chart, parent, false)
        return BarChartViewHolder(view)
    }

    override fun onBindViewHolder(holder: BarChartViewHolder, position: Int) {
            holder.setData(getItem(position))
    }

    inner class BarChartViewHolder(private val view:View) : RecyclerView.ViewHolder(view){
        private val chart: BarChart = view.findViewById(R.id.bar_chart)
        private val textView = view.findViewById<TextView>(R.id.title)
        private val descriptionTextView = view.findViewById<TextView>(R.id.description)

        init {
            chart.setDrawBarShadow(false);
            chart.setDrawValueAboveBar(true);

            chart.description.isEnabled = false;
//        chart.disableScroll()
            chart.setTouchEnabled(false)
            chart.isDoubleTapToZoomEnabled = false

            // if more than 60 entries are displayed in the chart, no values will be
            // drawn
            chart.setMaxVisibleValueCount(10);

            // scaling can now only be done on x- and y-axis separately
            chart.setPinchZoom(false);

            chart.setDrawGridBackground(false);
            // chart.setDrawYLabels(false);


            val xAxis = chart.xAxis;
            xAxis.position = XAxis.XAxisPosition.BOTTOM;
//        xAxis.setTypeface(tfLight);
            xAxis.setDrawGridLines(false);
            xAxis.setDrawLabels(true)
            xAxis.labelRotationAngle = 90f

            val custom = MyAxisValueFormatter();

            val leftAxis = chart.axisLeft;
//        leftAxis.setTypeface(tfLight);
            leftAxis.setLabelCount(5, true);
            leftAxis.valueFormatter = custom;
            leftAxis.axisLineWidth = 1f

            leftAxis.textColor = textView.currentTextColor
            xAxis.textColor = textView.currentTextColor

            xAxis.axisLineWidth = 1f

            leftAxis.axisLineColor = ResourcesCompat.getColor(view.context.resources, R.color.grey, null)
            xAxis.axisLineColor = ResourcesCompat.getColor(view.context.resources, R.color.grey, null)

            leftAxis.setDrawGridLines(false)
            leftAxis.setPosition(YAxis.YAxisLabelPosition.OUTSIDE_CHART);
            val rightAxis = chart.axisRight;
            rightAxis.isEnabled = false

             chart.legend.isEnabled = false
        }

         fun setData(data: BarChartModel) {
             textView.text = data.title
             descriptionTextView.text = data.description

            val xAxis = chart.xAxis
             xAxis.labelCount = data.axis.size
            xAxis.valueFormatter = DayAxisValueFormatter(data.axis.map { it.label });
            val values = ArrayList(data.axis.map { BarEntry(it.x.toFloat(), it.y.toFloat()) })

            val set1: BarDataSet

            if (chart.data != null &&
                    chart.data.dataSetCount > 0) {
                set1 = chart.data.getDataSetByIndex(0) as BarDataSet;
                set1.values = values;
                chart.data.notifyDataChanged();
                chart.notifyDataSetChanged();

            } else {
                set1 = BarDataSet(values, "")
                set1.setDrawIcons(false)
                set1.colors = listOf(ResourcesCompat.getColor(view.context.resources, R.color.colorPrimary, null))

                val dataSets = ArrayList<IBarDataSet>();
                dataSets.add(set1);

                val data = BarData(dataSets)
                data.setValueTextSize(10f)
                data.setValueTextColor(textView.currentTextColor)
//            data.setValueTypeface(tfLight);
//            data.barWidth = 1f;

                chart.data = data
            }
        }
    }
}