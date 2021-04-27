package com.echoeyecodes.scrub.callbacks

import com.echoeyecodes.scrub.models.Filter
import com.echoeyecodes.scrub.models.FilterModel

interface MainActivityCallBack {
    fun getFilters():HashMap<Filter, List<FilterModel>>
    fun setFilters(filter: HashMap<Filter, List<FilterModel>>)
    fun showRemoveAccountAlertDialog()
    fun removeAccount()
}