package com.echoeyecodes.ign.callbacks

import com.echoeyecodes.ign.models.Filter
import com.echoeyecodes.ign.models.FilterModel

interface MainActivityCallBack {
    fun getFilters():HashMap<Filter, List<FilterModel>>
    fun setFilters(filter: HashMap<Filter, List<FilterModel>>)
}