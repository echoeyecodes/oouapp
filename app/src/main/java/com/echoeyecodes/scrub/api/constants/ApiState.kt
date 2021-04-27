package com.echoeyecodes.scrub.api.constants


sealed class ApiState<out T : Any>{

    data class Error(val data: String) : ApiState<String>()

    object Loading : ApiState<Nothing>()
    object Unauthorized : ApiState<Nothing>()
    object Complete : ApiState<Nothing>()

}