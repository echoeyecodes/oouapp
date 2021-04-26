package com.echoeyecodes.dobby.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.TextView
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.echoeyecodes.dobby.utils.*
import com.echoeyecodes.ign.R
import com.echoeyecodes.ign.models.CourseModel

class CourseAdapter() : ListAdapter<CourseModel, CourseAdapter.CourseViewHolder>(CourseItemCallBack()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CourseViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.layout_result_item, parent, false)
        return CourseViewHolder(view)
    }

    override fun onBindViewHolder(holder: CourseViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class CourseViewHolder(private val view:View) : RecyclerView.ViewHolder(view){
        private val courseTitle = view.findViewById<TextView>(R.id.course_title)
        private val courseDesc = view.findViewById<TextView>(R.id.course_desc)
        private val courseCode = view.findViewById<TextView>(R.id.course_code)
        private val courseScore = view.findViewById<TextView>(R.id.course_score)
        private val progressBar = view.findViewById<ProgressBar>(R.id.progress)

        @SuppressLint("SetTextI18n")
        fun bind(model: CourseModel){
            courseTitle.text = "lol"
            courseDesc.text = "${model.session}  |  ${model.semester}"
            courseCode.text = model.code
            courseScore.text = "${model.score}%"
            progressBar.progress = model.score.toInt()
        }
    }

}