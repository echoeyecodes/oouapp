package com.echoeyecodes.ign.repositories

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.echoeyecodes.dobby.utils.AndroidUtilities
import com.echoeyecodes.ign.api.ApiClient
import com.echoeyecodes.ign.api.constants.ApiState
import com.echoeyecodes.ign.api.dao.StudentDao
import com.echoeyecodes.ign.models.CourseModel
import com.echoeyecodes.ign.models.UserModel
import com.echoeyecodes.ign.room.database.CourseDatabase
import kotlinx.coroutines.*
import retrofit2.HttpException

class CourseRepository(val context:Context) {
    private val database = CourseDatabase.getInstance(context)
    private val studentDao = ApiClient.getInstance(context).getClient(StudentDao::class.java)
    private val courseDao = database.courseDao()
    private val userDao = database.userDao()
    val state = MutableLiveData<ApiState<Any>>()

    fun getCoursesLiveData():LiveData<List<CourseModel>>{
        return courseDao.getCoursesLiveData()
    }

    fun addCourses(){
        CoroutineScope(Dispatchers.IO).launch {
            state.postValue(ApiState.Loading)
            try{
                val models = studentDao.getStudentData()
                userDao.insertUser(models.bio)
                courseDao.insertCourses(models.results)
                state.postValue(ApiState.Complete)
            }catch (exception:HttpException){
                if((exception.code() == 400 || exception.code() == 500) && exception.response() != null && exception.response()!!.errorBody() != null){
                    state.postValue(ApiState.Error(exception.response()!!.errorBody().toString()))
                }else{
                    state.postValue(ApiState.Error("An unknown error has occurred"))
                }
            }catch (exception:Exception){
                state.postValue(ApiState.Error("An unknown error has occurred"))
            }
        }
    }

    suspend fun getCourses() : List<CourseModel>{
        return courseDao.getCourses()
    }

    fun getUserLiveData(): LiveData<UserModel?>{
        return userDao.getUserLiveData()
    }

}