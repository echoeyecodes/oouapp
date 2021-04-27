package com.echoeyecodes.scrub.room.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.echoeyecodes.scrub.models.CourseModel
import com.echoeyecodes.scrub.models.UserModel
import com.echoeyecodes.scrub.room.dao.CourseDao
import com.echoeyecodes.scrub.room.dao.UserDao

@Database(entities = [CourseModel::class, UserModel::class], version = 1)
abstract class CourseDatabase : RoomDatabase() {
    abstract fun courseDao(): CourseDao
    abstract fun userDao(): UserDao

    companion object {
        @Volatile
        private var INSTANCE: CourseDatabase? = null
        fun getInstance(context: Context) = INSTANCE ?: synchronized(this) {
            val instance = Room.databaseBuilder(context.applicationContext, CourseDatabase::class.java, "course_db")
                    .fallbackToDestructiveMigration().build()
            INSTANCE = instance
            return instance
        }
    }
}