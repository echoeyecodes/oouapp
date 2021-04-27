package com.echoeyecodes.scrub.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.echoeyecodes.scrub.models.Filter
import com.echoeyecodes.scrub.models.FilterModel


class FilterFragmentViewModelFactory(private val filters: HashMap<Filter, List<FilterModel>>) : ViewModelProvider.Factory{
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(FilterFragmentViewModel::class.java)) {
            return FilterFragmentViewModel(filters) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}

class FilterFragmentViewModel(val filters: HashMap<Filter, List<FilterModel>>): ViewModel() {
    val filtersLiveData = MutableLiveData(filters)

    fun onFilterSelected(name:Filter, filter: FilterModel){
        val data = HashMap(filtersLiveData.value!!)
        val list = data[name]!!.map {
            if(it.name == filter.name){
                it.selected = !it.selected
            }
            it
        }
        data[name] = list
        filtersLiveData.value = data
    }
}