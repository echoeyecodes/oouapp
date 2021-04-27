package com.echoeyecodes.scrub.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.echoeyecodes.dobby.utils.FilterItemCallBack
import com.echoeyecodes.scrub.R
import com.echoeyecodes.scrub.callbacks.FilterFragmentCallback
import com.echoeyecodes.scrub.models.Filter
import com.echoeyecodes.scrub.models.FilterModel
import com.google.android.material.chip.Chip

class FilterAdapter(val name:Filter, val listener:FilterFragmentCallback) : ListAdapter<FilterModel, FilterAdapter.FilterViewHolder>(FilterItemCallBack()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FilterViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.layout_filter_item, parent, false)
        return FilterViewHolder(view)
    }

    override fun onBindViewHolder(holder: FilterViewHolder, position: Int) {
            holder.bindData(getItem(position))
    }

    inner class FilterViewHolder(view:View) : RecyclerView.ViewHolder(view){
        private val chip = view.findViewById<Chip>(R.id.chip)

        fun bindData(model: FilterModel){
            chip.text = model.name
            chip.setOnCheckedChangeListener { _, isChecked ->
                model.selected = !isChecked
                listener.onFilterSelected(name, model)
            }
            chip.isChecked = model.selected
        }
    }
}