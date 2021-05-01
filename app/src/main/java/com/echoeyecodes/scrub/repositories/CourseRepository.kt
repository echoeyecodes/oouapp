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
import com.echoeyecodes.scrub.api.utils.RequestManager
import com.echoeyecodes.scrub.api.utils.RequestManagerAction
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
    private val requestManager = RequestManager(state)

    fun getCoursesLiveData():LiveData<List<CourseModel>>{
        return courseDao.getCoursesLiveData()
    }

    fun addCourses(){
        requestManager.executeWithManager(object : RequestManagerAction<Unit> {
            override suspend fun execute(){
                val models = studentDao.getStudentData()
                userDao.deleteUsers()
                userDao.insertUser(models.bio)
                return courseDao.insertCourses(models.results)
            }
        })
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