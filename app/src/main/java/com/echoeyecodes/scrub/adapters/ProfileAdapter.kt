package com.echoeyecodes.scrub.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.echoeyecodes.dobby.utils.UserItemCallBack
import com.echoeyecodes.scrub.R
import com.echoeyecodes.scrub.callbacks.MainActivityCallBack
import com.echoeyecodes.scrub.models.UserModel
import de.hdodenhof.circleimageview.CircleImageView

class ProfileAdapter(val listener:MainActivityCallBack) : ListAdapter<UserModel, ProfileAdapter.FilterViewHolder>(UserItemCallBack()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FilterViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.layout_profile, parent, false)
        return FilterViewHolder(view)
    }

    override fun onBindViewHolder(holder: FilterViewHolder, position: Int) {
            holder.bindData(getItem(position))
    }

    inner class FilterViewHolder(private val view:View) : RecyclerView.ViewHolder(view){
        private val level = view.findViewById<TextView>(R.id.level)
        private val jambScore = view.findViewById<TextView>(R.id.jamb_score)
        private val matricNumber = view.findViewById<TextView>(R.id.matric_number)
        private val gender = view.findViewById<TextView>(R.id.gender)
        private val name = view.findViewById<TextView>(R.id.name)
        private val btn = view.findViewById<RelativeLayout>(R.id.btn)
        private val course = view.findViewById<TextView>(R.id.course)
        private val imageView = view.findViewById<CircleImageView>(R.id.image)

        fun bindData(model: UserModel){
            name.text = model.name
            jambScore.text = model.jambScore
            matricNumber.text = model.matricNo
            gender.text = model.gender
            level.text = model.level
            course.text = model.course
            Glide.with(view).load(model.thumbnail).into(imageView)

            btn.setOnClickListener { listener.showRemoveAccountAlertDialog() }
        }
    }
}