package com.echoeyecodes.scrub.api.dao


import com.echoeyecodes.scrub.api.model.AnalyticsResponseModel
import com.echoeyecodes.scrub.api.model.StudentResponseModel
import retrofit2.http.GET

interface StudentDao {
    @GET("api/v1/user")
    suspend fun getStudentData(): StudentResponseModel

    @GET("api/v1/user/analytics")
    suspend fun getAnalytics(): AnalyticsResponseModel
}