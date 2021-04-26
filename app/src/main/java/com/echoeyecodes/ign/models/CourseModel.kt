package com.echoeyecodes.ign.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "courses")
data class CourseModel(@PrimaryKey val code:String, val semester:String, val session:String, val units:String, val score:String, val grade:String)