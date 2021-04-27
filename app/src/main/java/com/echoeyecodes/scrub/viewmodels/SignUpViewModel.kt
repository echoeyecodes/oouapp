package com.echoeyecodes.scrub.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.echoeyecodes.scrub.api.constants.ApiState
import com.echoeyecodes.scrub.api.model.LoginModel
import com.echoeyecodes.scrub.models.FieldErrorStatus
import com.echoeyecodes.scrub.repositories.LoginRepository
import com.echoeyecodes.scrub.utils.AndroidUtilities

class SignUpViewModel(application: Application) : AndroidViewModel(application){
    val fields = HashMap<String, String>().apply {
        put("matricnumber", "")
        put("password", "")
    }
    var isValid = true
    val errorObserver = MutableLiveData<FieldErrorStatus>()
    private val loginRepository = LoginRepository(application)


    fun getStatus() : LiveData<ApiState<Any>>{
        return loginRepository.state
    }

    private fun validateFields() {
        isValid = !(fields["matricnumber"] == "" || fields["password"] == "")
    }

    fun setFormField(name:String, value:String){
        fields[name] = value
    }

    fun validateField(name:String){
        if(fields[name] == ""){
            val fieldErrorStatus = FieldErrorStatus(name, name.plus(" cannot be empty"), true)
            errorObserver.value = fieldErrorStatus
            isValid = false
        }else{
            errorObserver.value = FieldErrorStatus("","".plus(" cannot be empty"), true)
            isValid = true
        }
    }


    fun submitForm() {
        validateFields()
        if(!isValid){
            AndroidUtilities.showToastMessage(getApplication(), "One or more fields cannot be empty")
        }else{
            loginRepository.login(LoginModel(fields["matricnumber"]!!, fields["password"]!!))
        }
    }
}