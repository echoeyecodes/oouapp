package com.echoeyecodes.scrub.adapters.chartadapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.echoeyecodes.dobby.utils.DefaultItemCallBack
import com.echoeyecodes.scrub.R

class OverallCGPAChartAdapter : ListAdapter<String, OverallCGPAChartAdapter.OverallCGPAChartViewHolder>(DefaultItemCallBack()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OverallCGPAChartViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.layout_overall_cgpa, parent, false)
        return OverallCGPAChartViewHolder(view)
    }

    override fun onBindViewHolder(holder: OverallCGPAChartViewHolder, position: Int) {

    }

    inner class OverallCGPAChartViewHolder(view:View) : RecyclerView.ViewHolder(view)
}