package com.echoeyecodes.scrub.repositories

import android.app.Activity
import android.content.Context
import android.content.Intent
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.echoeyecodes.scrub.activities.SignUpActivity
import com.echoeyecodes.scrub.api.ApiClient
import com.echoeyecodes.scrub.api.constants.ApiState
import com.echoeyecodes.scrub.api.dao.StudentDao
import com.echoeyecodes.scrub.api.model.AnalyticsResponseModel
import com.echoeyecodes.scrub.api.utils.RequestManager
import com.echoeyecodes.scrub.api.utils.RequestManagerAction
import com.echoeyecodes.scrub.models.CourseModel
import com.echoeyecodes.scrub.models.UserModel
import com.echoeyecodes.scrub.room.database.CourseDatabase
import com.echoeyecodes.scrub.utils.AndroidUtilities
import kotlinx.coroutines.*
import retrofit2.HttpException

class AnalyticsRepository(val context:Context) {
    private val studentDao = ApiClient.getInstance(context).getClient(StudentDao::class.java)
    val state = MutableLiveData<ApiState<Any>>()
    private val requestManager = RequestManager(state)

    fun getAnalytics(){
        requestManager.executeWithManager(object : RequestManagerAction<AnalyticsResponseModel>{
            override suspend fun execute() : AnalyticsResponseModel {
                return studentDao.getAnalytics()
            }
        })
    }

}