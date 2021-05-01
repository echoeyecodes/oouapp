package com.echoeyecodes.scrub.adapters.chartadapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.res.ResourcesCompat
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.echoeyecodes.dobby.utils.CustomItemDecoration
import com.echoeyecodes.dobby.utils.DefaultItemCallBack
import com.echoeyecodes.dobby.utils.PieChartItemCallBack
import com.echoeyecodes.scrub.R
import com.echoeyecodes.scrub.models.LegendModel
import com.echoeyecodes.scrub.models.PieChartModel
import com.echoeyecodes.scrub.utils.AndroidUtilities
import com.echoeyecodes.scrub.utils.PercentValueFormatter
import com.github.mikephil.charting.charts.PieChart
import com.github.mikephil.charting.components.Legend
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import com.github.mikephil.charting.utils.MPPointF
import com.google.android.flexbox.*

class PieChartAdapter : ListAdapter<PieChartModel, PieChartAdapter.PieChartViewHolder>(PieChartItemCallBack()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PieChartViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.layout_pie_chart, parent, false)
        return PieChartViewHolder(view)
    }

    override fun onBindViewHolder(holder: PieChartViewHolder, position: Int) {
            holder.setData(getItem(position))
    }

    inner class PieChartViewHolder(private val view: View) : RecyclerView.ViewHolder(view) {
        private val pieChart = view.findViewById<PieChart>(R.id.pie_chart)
        private val textView = view.findViewById<TextView>(R.id.title)
        private val recyclerView = view.findViewById<RecyclerView>(R.id.recycler_view)
        private val descriptionTextView = view.findViewById<TextView>(R.id.description)
        private var legendAdapter: LegendAdapter? = null

        init {
            val layoutManager = FlexboxLayoutManager(view.context, FlexDirection.ROW, FlexWrap.WRAP)
            layoutManager.alignItems = AlignItems.FLEX_START
            val itemDecoration = CustomItemDecoration(5)

            recyclerView.addItemDecoration(itemDecoration)
            recyclerView.layoutManager = layoutManager

            pieChart.apply {
                setUsePercentValues(false)
                description.isEnabled = false
                setExtraOffsets(5f, 10f, 5f, 5f)
                dragDecelerationFrictionCoef = 0.95f
                centerText = ""
                setDrawEntryLabels(false)
                isDrawHoleEnabled = true
                setHoleColor(ResourcesCompat.getColor(view.context.resources, android.R.color.transparent, null))
                setTransparentCircleColor(ResourcesCompat.getColor(resources, R.color.design_default_color_on_primary, null))
                setTransparentCircleAlpha(110)
                holeRadius = 20f
                transparentCircleRadius = 30f
                setDrawCenterText(true)
                rotationAngle = 0f
                isRotationEnabled = true
                isHighlightPerTapEnabled = true
            }

            //legend
            pieChart.legend.isEnabled = false

            //chart slice label
            pieChart.setEntryLabelColor(ResourcesCompat.getColor(view.context.resources, R.color.white, null));
            pieChart.setEntryLabelTextSize(14f)
        }

         fun setData(data: PieChartModel){
             textView.text = data.title
             descriptionTextView.text = data.description
             val entry = ArrayList(data.slices.map { PieEntry(it.value, it.label)})

            val dataSet = PieDataSet(entry, "").apply {
                setDrawIcons(false)
                sliceSpace = 3f
                iconsOffset = MPPointF(0f, 0f)
                selectionShift = 5f
            }

            val colors = ArrayList<Int>().apply {
                add(ResourcesCompat.getColor(view.context.resources, R.color.grade_a, null))
                add(ResourcesCompat.getColor(view.context.resources, R.color.grade_b, null))
                add(ResourcesCompat.getColor(view.context.resources, R.color.grade_c, null))
                add(ResourcesCompat.getColor(view.context.resources, R.color.grade_d, null))
                add(ResourcesCompat.getColor(view.context.resources, R.color.grade_e, null))
                add(ResourcesCompat.getColor(view.context.resources, R.color.grade_f, null))
            }

             legendAdapter = LegendAdapter(data.total)
             recyclerView.adapter = legendAdapter

             val legends = data.slices.map {
                 val color = when(it.label.toUpperCase()){
                     "A" -> ResourcesCompat.getColor(view.context.resources, R.color.grade_a, null)
                     "B" -> ResourcesCompat.getColor(view.context.resources, R.color.grade_b, null)
                     "C" -> ResourcesCompat.getColor(view.context.resources, R.color.grade_c, null)
                     "D" -> ResourcesCompat.getColor(view.context.resources, R.color.grade_d, null)
                     "E" -> ResourcesCompat.getColor(view.context.resources, R.color.grade_e, null)
                     else -> ResourcesCompat.getColor(view.context.resources, R.color.grade_f, null)
                 }
                 LegendModel(color, it.label, it.value)
             }

             legendAdapter?.submitList(legends)

            dataSet.colors = colors

            val pieData = PieData(dataSet)

             pieData.setValueFormatter(PercentValueFormatter(data.total))
             pieData.setValueTextSize(11f)
             pieData.setValueTextColor(ResourcesCompat.getColor(view.context.resources, R.color.white, null))
//        data.setValueTypeface(tfLight)


            pieChart.data = pieData

            // undo all highlights

            // undo all highlights
            pieChart.highlightValues(null)

            pieChart.invalidate()
        }
    }
}