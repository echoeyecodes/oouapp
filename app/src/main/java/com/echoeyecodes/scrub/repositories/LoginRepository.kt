package com.echoeyecodes.scrub.repositories

import android.content.Context
import androidx.lifecycle.MutableLiveData
import com.echoeyecodes.scrub.api.ApiClient
import com.echoeyecodes.scrub.api.constants.ApiState
import com.echoeyecodes.scrub.api.dao.LoginDao
import com.echoeyecodes.scrub.api.model.LoginModel
import com.echoeyecodes.scrub.api.utils.RequestManager
import com.echoeyecodes.scrub.api.utils.RequestManagerAction

class LoginRepository(val context:Context) {
    val state = MutableLiveData<ApiState<Any>>()
    private val loginDao = ApiClient.getInstance(context).getClient(LoginDao::class.java)
    private val requestManager = RequestManager(state)

    fun login(model:LoginModel){
        requestManager.executeWithManager(object : RequestManagerAction<Unit> {
            override suspend fun execute() {
                val token = loginDao.login(model)
                return saveToken(token)
            }
        })
    }

    private fun saveToken(token: String) {
        val sharedPref = context.getSharedPreferences("auth", Context.MODE_PRIVATE)
        sharedPref.edit().apply {
            putString("token", token)
            apply()
        }
    }
}