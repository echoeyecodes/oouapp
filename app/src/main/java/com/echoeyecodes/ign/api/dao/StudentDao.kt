package com.echoeyecodes.ign.api.dao


import com.echoeyecodes.ign.api.model.StudentResponseModel
import retrofit2.http.GET
import retrofit2.http.Query

interface StudentDao {
    @GET("api/v1/user")
    suspend fun getStudentData(): StudentResponseModel
}