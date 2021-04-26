package com.echoeyecodes.ign.room.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.echoeyecodes.ign.models.CourseModel
import com.echoeyecodes.ign.models.UserModel
import com.echoeyecodes.ign.room.dao.CourseDao
import com.echoeyecodes.ign.room.dao.UserDao

@Database(entities = [CourseModel::class, UserModel::class], version = 2)
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