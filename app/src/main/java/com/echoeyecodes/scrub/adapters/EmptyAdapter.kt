package com.echoeyecodes.scrub.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.echoeyecodes.dobby.utils.DefaultItemCallBack
import com.echoeyecodes.scrub.R

class EmptyAdapter() : ListAdapter<String, EmptyAdapter.EmptyViewHolder>(DefaultItemCallBack()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EmptyViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.layout_empty, parent, false)
        return EmptyViewHolder(view)
    }

    override fun onBindViewHolder(holder: EmptyViewHolder, position: Int) {

    }

    inner class EmptyViewHolder(view:View) : RecyclerView.ViewHolder(view)
}