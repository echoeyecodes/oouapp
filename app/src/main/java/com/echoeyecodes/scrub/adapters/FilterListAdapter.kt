package com.echoeyecodes.scrub.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.echoeyecodes.dobby.utils.*
import com.echoeyecodes.scrub.R
import com.echoeyecodes.scrub.callbacks.FilterFragmentCallback
import com.echoeyecodes.scrub.models.Filter
import com.echoeyecodes.scrub.models.FilterListModel
import com.google.android.flexbox.FlexDirection
import com.google.android.flexbox.FlexWrap
import com.google.android.flexbox.FlexboxLayoutManager

class FilterListAdapter(val listener:FilterFragmentCallback) : ListAdapter<FilterListModel, FilterListAdapter.FilterListViewHolder>(FilterListItemCallBack()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FilterListViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.layout_filter_list_item, parent, false)
        return when(viewType){
            0 -> FilterListViewHolder(Filter.LEVEL, view)
            1 -> FilterListViewHolder(Filter.SEMESTER, view)
            else -> FilterListViewHolder(Filter.UNITS, view)
        }
    }

    override fun onBindViewHolder(holder: FilterListViewHolder, position: Int) {
        holder.bindData(getItem(position))
    }

    override fun getItemViewType(position: Int): Int {
        return getItem(position).title.ordinal
    }

    inner class FilterListViewHolder(val name:Filter, view:View) : RecyclerView.ViewHolder(view){
        private val textView = view.findViewById<TextView>(R.id.text_view)
        private val recyclerView = view.findViewById<RecyclerView>(R.id.recycler_view)
        private val adapter: FilterAdapter

        init {
            val layoutManager = FlexboxLayoutManager(view.context, FlexDirection.ROW, FlexWrap.WRAP)
            recyclerView.layoutManager = layoutManager
            val itemDecoration = CustomItemDecoration(5, 5)
            recyclerView.addItemDecoration(itemDecoration)
            adapter = FilterAdapter(name, listener)
            recyclerView.adapter = adapter
        }

        fun bindData(model: FilterListModel){
            textView.text = name.name
            adapter.submitList(model.data)
        }
    }
}