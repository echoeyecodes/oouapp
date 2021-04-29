package com.echoeyecodes.scrub.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.echoeyecodes.scrub.api.constants.ApiState
import com.echoeyecodes.scrub.repositories.AnalyticsRepository

class AnalyticsViewModel(application: Application) : AndroidViewModel(application){

    val analyticsRepository = AnalyticsRepository(application)

    init {
        fetchData()
    }

    fun fetchData(){
        analyticsRepository.getAnalytics()
    }

    fun getNetworkState(): LiveData<ApiState<Any>>{
        return analyticsRepository.state
    }

}