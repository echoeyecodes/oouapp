package com.echoeyecodes.dobby.adapters

import android.annotation.SuppressLint
import android.content.res.ColorStateList
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.TextView
import androidx.core.content.res.ResourcesCompat
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.echoeyecodes.dobby.utils.*
import com.echoeyecodes.scrub.R
import com.echoeyecodes.scrub.models.CourseModel

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
        private val grade = view.findViewById<TextView>(R.id.grade)
        private val progressBar = view.findViewById<ProgressBar>(R.id.progress)

        @SuppressLint("SetTextI18n")
        fun bind(model: CourseModel){
            courseTitle.text = "${model.units} units"
            courseDesc.text = "${model.session}  |  ${model.semester}"
            courseCode.text = model.code
            courseScore.text = "${model.score}%"
            grade.text = model.grade
            progressBar.progress = model.score.toInt()

            when(model.grade){
                "A", "a" -> {
                    grade.background = ResourcesCompat.getDrawable(view.resources, R.drawable.drawable_circle_a, null)
                    progressBar.progressTintList = ColorStateList.valueOf(ResourcesCompat.getColor(view.resources, R.color.grade_a, null))
                }
                "B", "b" -> {
                    grade.background = ResourcesCompat.getDrawable(view.resources, R.drawable.drawable_circle_b, null)
                    progressBar.progressTintList = ColorStateList.valueOf(ResourcesCompat.getColor(view.resources, R.color.grade_b, null))
                }
                "C", "c" -> {
                    grade.background = ResourcesCompat.getDrawable(view.resources, R.drawable.drawable_circle_c, null)
                    progressBar.progressTintList = ColorStateList.valueOf(ResourcesCompat.getColor(view.resources, R.color.grade_c, null))
                }
                "D", "d" -> {
                    grade.background = ResourcesCompat.getDrawable(view.resources, R.drawable.drawable_circle_d, null)
                    progressBar.progressTintList = ColorStateList.valueOf(ResourcesCompat.getColor(view.resources, R.color.grade_d, null))
                }
                "E", "e" -> {
                    grade.background = ResourcesCompat.getDrawable(view.resources, R.drawable.drawable_circle_e, null)
                    progressBar.progressTintList = ColorStateList.valueOf(ResourcesCompat.getColor(view.resources, R.color.grade_e, null))
                }
                else -> {
                    grade.background = ResourcesCompat.getDrawable(view.resources, R.drawable.drawable_circle_f, null)
                    progressBar.progressTintList = ColorStateList.valueOf(ResourcesCompat.getColor(view.resources, R.color.grade_f, null))
                }
            }
        }
    }

}