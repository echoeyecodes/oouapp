package com.echoeyecodes.ign.api.dao

import com.echoeyecodes.ign.api.model.LoginModel
import retrofit2.http.Body
import retrofit2.http.POST

interface LoginDao {
    @POST("api/v1/auth/login")
    suspend fun login( @Body model:LoginModel): String
}