package com.echoeyecodes.ign.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.echoeyecodes.dobby.utils.AndroidUtilities
import com.echoeyecodes.ign.api.constants.ApiState
import com.echoeyecodes.ign.api.model.LoginModel
import com.echoeyecodes.ign.models.FieldErrorStatus
import com.echoeyecodes.ign.repositories.LoginRepository

class SignUpViewModel(application: Application) : AndroidViewModel(application){
    var matricnumber = ""
    var password = ""
    var isValid = true
    val errorObserver = MutableLiveData<FieldErrorStatus>()
    private val loginRepository = LoginRepository(application)


    fun getStatus() : LiveData<ApiState<Any>>{
        return loginRepository.state
    }

    fun validateFields() {
        when {
            matricnumber == "" -> {
                val fieldErrorStatus = FieldErrorStatus("matricnumber","Matric Number".plus(" cannot be empty"), true)
                errorObserver.value = fieldErrorStatus
                isValid = false
            }
            password == "" -> {
                val fieldErrorStatus = FieldErrorStatus("password","Password".plus(" cannot be empty"), true)
                errorObserver.value = fieldErrorStatus
                isValid = false
            }
            else -> {
                errorObserver.value = FieldErrorStatus("","".plus(" cannot be empty"), true)
                isValid = true
            }
        }
    }


    fun submitForm() {
        validateFields()
        if(!isValid){
            AndroidUtilities.showToastMessage(getApplication(), "One or more fields cannot be empty")
        }else{
            loginRepository.login(LoginModel(matricnumber, password))
        }
    }
}