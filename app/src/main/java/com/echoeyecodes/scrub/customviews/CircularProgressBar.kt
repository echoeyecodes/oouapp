package com.echoeyecodes.scrub.customviews

import android.content.Context
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.widget.ProgressBar
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.core.content.res.ResourcesCompat
import com.echoeyecodes.scrub.R

class CircularProgressBar(context: Context, attrs: AttributeSet) : RelativeLayout(context, attrs) {
    private lateinit var progressBar: ProgressBar
    private lateinit var percentage: TextView
    private lateinit var description: TextView

    private fun init(context: Context, attributeSet: AttributeSet) {
        inflate(context, R.layout.layout_circular_progress, this)
        val typedArray = context.theme.obtainStyledAttributes(attributeSet, R.styleable.CustomProgressBar, 0, 0)
        initViews()
        try {
            val progress = typedArray.getInt(R.styleable.CustomProgressBar_pd_progress, 0)
            val title = typedArray.getString(R.styleable.CustomProgressBar_pd_text)
            val description = typedArray.getString(R.styleable.CustomProgressBar_pd_description)
            val background = typedArray.getInt(R.styleable.CustomProgressBar_pd_custom_background_drawable, R.drawable.drawable_circular_progress_background)
            setText(title)
            setDescription(description)
            setProgress(progress)
            setCustomBackgroundDrawable(ResourcesCompat.getDrawable(resources, background, null)!!)
        } finally {
            run { typedArray.recycle() }
        }
    }

    private fun initViews() {
        progressBar = findViewById(R.id.progress_bar)
        percentage = findViewById(R.id.title)
        description = findViewById(R.id.description)
    }

    private fun setText(title: String?) {
        this.percentage.text = "$title%"
    }

    private fun setCustomBackgroundDrawable(drawable: Drawable) {
        progressBar.progressDrawable
        refreshLayout()
    }

    private fun setProgress(progress: Int) {
        this.progressBar.progress = progress
        refreshLayout()
    }

    private fun setDescription(description: String?) {
        this.description.text = description
        refreshLayout()
    }

    private fun refreshLayout() {
        invalidate()
        requestLayout()
    }


    init {
        init(context, attrs)
    }
}