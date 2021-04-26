package com.echoeyecodes.ign.callbacks

import com.echoeyecodes.ign.models.Filter
import com.echoeyecodes.ign.models.FilterModel

interface FilterFragmentCallback {
    fun onFilterSelected(name: Filter, filter: FilterModel)
}