package com.echoeyecodes.scrub.room.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.echoeyecodes.scrub.models.CourseModel

@Dao
abstract class CourseDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract suspend fun insertCourses(models: List<CourseModel>)

    @Query("DELETE FROM courses")
    abstract suspend fun deleteCourses()

    @Query("SELECT * FROM courses")
    abstract fun getCoursesLiveData(): LiveData<List<CourseModel>>

    @Query("SELECT * FROM courses")
    abstract suspend fun getCourses(): List<CourseModel>

}