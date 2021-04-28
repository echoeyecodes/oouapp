package com.echoeyecodes.scrub.repositories

import android.app.Activity
import android.content.Context
import android.content.Intent
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.echoeyecodes.scrub.activities.SignUpActivity
import com.echoeyecodes.scrub.api.ApiClient
import com.echoeyecodes.scrub.api.constants.ApiState
import com.echoeyecodes.scrub.api.dao.StudentDao
import com.echoeyecodes.scrub.models.CourseModel
import com.echoeyecodes.scrub.models.UserModel
import com.echoeyecodes.scrub.room.database.CourseDatabase
import com.echoeyecodes.scrub.utils.AndroidUtilities
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
                userDao.deleteUsers()
                userDao.insertUser(models.bio)
                courseDao.insertCourses(models.results)
                state.postValue(ApiState.Complete)
            }catch (exception:HttpException){
                if((exception.code() == 400 || exception.code() == 500) && exception.response() != null && exception.response()!!.errorBody() != null){
                    state.postValue(ApiState.Error(exception.response()!!.errorBody()?.string() ?: "An unknown error has occurred"))
                }else if(exception.code() == 401){
                    state.postValue(ApiState.Unauthorized)
                }else{
                    state.postValue(ApiState.Error("An unknown error has occurred"))
                }
            }catch (exception:Exception){
                state.postValue(ApiState.Error("Could not establish a connection with the server"))
            }
        }
    }

    suspend fun getCourses() : List<CourseModel>{
        return courseDao.getCourses()
    }

    fun getUserLiveData(): LiveData<UserModel?>{
        return userDao.getUserLiveData()
    }

    suspend fun deleteData(){
        courseDao.deleteCourses()
        userDao.deleteUsers()
    }

}