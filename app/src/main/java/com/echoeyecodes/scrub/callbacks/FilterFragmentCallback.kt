package com.echoeyecodes.scrub.callbacks

import com.echoeyecodes.scrub.models.Filter
import com.echoeyecodes.scrub.models.FilterModel

interface FilterFragmentCallback {
    fun onFilterSelected(name: Filter, filter: FilterModel)
}