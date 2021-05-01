package com.echoeyecodes.scrub.adapters.chartadapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.echoeyecodes.dobby.utils.LegendItemCallBack
import com.echoeyecodes.scrub.R
import com.echoeyecodes.scrub.models.LegendModel
import com.echoeyecodes.scrub.utils.AndroidUtilities
import kotlin.math.roundToInt

class LegendAdapter(private val total:Int) : ListAdapter<LegendModel, LegendAdapter.LegendViewHolder>(LegendItemCallBack()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LegendViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.layout_legend_item, parent, false)
        return LegendViewHolder(view)
    }

    override fun onBindViewHolder(holder: LegendViewHolder, position: Int) {
            holder.bindAdapter(getItem(position))
    }

    inner class LegendViewHolder(view:View) : RecyclerView.ViewHolder(view){
        private val textView = view.findViewById<TextView>(R.id.legend_text)
        private val box = view.findViewById<RelativeLayout>(R.id.legend_box)

        @SuppressLint("SetTextI18n")
        fun bindAdapter(model:LegendModel){
            val result = ((model.value/100) * total).roundToInt()
            val text = if (result == 1){
                "$result course"
            }else{
                "$result courses"
            }
            textView.text = "${model.text} (${text})"
            box.setBackgroundColor(model.color)
        }
    }
}