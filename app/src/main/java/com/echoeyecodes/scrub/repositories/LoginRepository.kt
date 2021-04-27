package com.echoeyecodes.scrub.repositories

import android.content.Context
import androidx.lifecycle.MutableLiveData
import com.echoeyecodes.scrub.api.ApiClient
import com.echoeyecodes.scrub.api.constants.ApiState
import com.echoeyecodes.scrub.api.dao.LoginDao
import com.echoeyecodes.scrub.api.model.LoginModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.HttpException

class LoginRepository(val context:Context) {
    val state = MutableLiveData<ApiState<Any>>()
    private val loginDao = ApiClient.getInstance(context).getClient(LoginDao::class.java)

    fun login(model:LoginModel){
        CoroutineScope(Dispatchers.IO).launch {
            try{
                state.postValue(ApiState.Loading)
                val token = loginDao.login(model)
                saveToken(token)
                state.postValue(ApiState.Complete)
            }catch(exception: HttpException){
                if((exception.code() == 400 || exception.code() == 500) && exception.response() != null && exception.response()!!.errorBody() != null){
                    state.postValue(ApiState.Error(exception.response()!!.errorBody()?.string() ?: "An unknown error has occurred"))
                }else{
                    state.postValue(ApiState.Error("An unknown error has occurred"))
                }
            }catch(exception:Exception){
                state.postValue(ApiState.Error("Could not establish a connection with the server"))
            }
        }
    }

    private fun saveToken(token: String) {
        val sharedPref = context.getSharedPreferences("auth", Context.MODE_PRIVATE)
        sharedPref.edit().apply {
            putString("token", token)
            apply()
        }
    }
}