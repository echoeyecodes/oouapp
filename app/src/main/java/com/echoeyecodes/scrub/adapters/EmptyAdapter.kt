package com.echoeyecodes.scrub.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.echoeyecodes.dobby.utils.DefaultItemCallBack
import com.echoeyecodes.dobby.utils.EmptyItemCallBack
import com.echoeyecodes.scrub.R
import com.echoeyecodes.scrub.models.EmptyModel

class EmptyAdapter() : ListAdapter<EmptyModel, EmptyAdapter.EmptyViewHolder>(EmptyItemCallBack()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EmptyViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.layout_empty, parent, false)
        return EmptyViewHolder(view)
    }

    override fun onBindViewHolder(holder: EmptyViewHolder, position: Int) {
        holder.bindData(getItem(position))
    }

    inner class EmptyViewHolder(val view:View) : RecyclerView.ViewHolder(view){
        private val image = view.findViewById<ImageView>(R.id.image)
        private val textView = view.findViewById<TextView>(R.id.text)

        fun bindData(model: EmptyModel){
            Glide.with(view).load(model.image).into(image)
            textView.text = model.text
        }
    }
}