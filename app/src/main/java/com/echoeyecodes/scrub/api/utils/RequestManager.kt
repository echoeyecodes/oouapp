package com.echoeyecodes.scrub.api.utils

import android.app.Activity
import android.app.ActivityManager
import android.content.Context
import android.content.Intent
import androidx.lifecycle.MutableLiveData
import com.echoeyecodes.scrub.activities.SignUpActivity
import com.echoeyecodes.scrub.api.constants.ApiState
import com.echoeyecodes.scrub.repositories.CourseRepository
import com.echoeyecodes.scrub.utils.AndroidUtilities
import com.echoeyecodes.scrub.utils.AuthManager
import com.echoeyecodes.scrub.utils.MyApplication
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import retrofit2.HttpException

interface RequestManagerAction<T>{
    suspend fun execute():T
}

class RequestManager(private val state:MutableLiveData<ApiState<Any>>) {

    fun <T:Any>executeWithManager(execution:RequestManagerAction<T>){
            CoroutineScope(Dispatchers.IO).launch {
                try{
                    state.postValue(ApiState.Loading)
                    val result = execution.execute()
                    state.postValue(ApiState.Complete(result))
            } catch (exception: HttpException){
            if((exception.code() == 400 || exception.code() == 500) && exception.response() != null && exception.response()!!.errorBody() != null){
                state.postValue(ApiState.Error(exception.response()!!.errorBody()?.string() ?: "An unknown error has occurred"))
            }else if(exception.code() == 401){
                AuthManager().logOut()
                state.postValue(ApiState.Unauthorized)
            }else{
                state.postValue(ApiState.Error("An unknown error has occurred"))
            }
            AndroidUtilities.log("message is ${exception.message()}")
        }catch (exception:Exception){
            state.postValue(ApiState.Error("Could not establish a connection with the server"))
            AndroidUtilities.log("message is ${exception.message}")
                }
            }
    }
}