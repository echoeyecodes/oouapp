package com.echoeyecodes.scrub.api.dao

import com.echoeyecodes.scrub.api.model.LoginModel
import retrofit2.http.Body
import retrofit2.http.POST

interface LoginDao {
    @POST("api/v1/auth/login")
    suspend fun login( @Body model:LoginModel): String
}